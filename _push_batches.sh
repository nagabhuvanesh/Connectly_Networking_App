#!/bin/bash
set -e
cd "C:/@Work/POC"

BUDGET=10000000  # ~10MB per commit/push

# List remaining untracked files git would track (respects .gitignore), stable order
git -c core.quotepath=false ls-files -o --exclude-standard -z > /tmp/allfiles.z

batch=()
batch_size=0
batch_num=8

flush() {
  if [ "${#batch[@]}" -eq 0 ]; then
    return
  fi
  batch_num=$((batch_num+1))
  printf '%s\0' "${batch[@]}" > /tmp/batch_files.z
  git add --pathspec-from-file=/tmp/batch_files.z --pathspec-file-nul
  git commit -q -m "Add project files (batch $batch_num)"
  echo "=== Committed batch $batch_num: ${#batch[@]} files, ${batch_size} bytes ==="
  for attempt in 1 2 3 4 5; do
    if git push origin main 2>&1 | tee /tmp/push_out.log; then
      if ! grep -qi "error\|fatal" /tmp/push_out.log; then
        break
      fi
    fi
    echo "--- push attempt $attempt for batch $batch_num failed, retrying ---"
    sleep 5
  done
  batch=()
  batch_size=0
}

while IFS= read -r -d '' f; do
  sz=$(stat -c%s "$f" 2>/dev/null || echo 0)
  if [ "$batch_size" -gt 0 ] && [ $((batch_size + sz)) -gt "$BUDGET" ]; then
    flush
  fi
  batch+=("$f")
  batch_size=$((batch_size + sz))
done < /tmp/allfiles.z

flush

echo "=== ALL BATCHES DONE ==="
git log --oneline | head -50
