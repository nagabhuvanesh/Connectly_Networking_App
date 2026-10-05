# Connectly

Connectly is a networking/events companion app built with **Kotlin Multiplatform** and **Compose Multiplatform**. A single shared codebase (`Connectly/composeApp`) targets:

- **Android** (phone/tablet, API 26+)
- **iOS** (via a generated `ComposeApp.framework`, wrapped by a thin SwiftUI shell in `Connectly/iosApp`)
- **Web** (via Kotlin/Wasm, running in any modern Wasm-capable browser)

All UI, navigation, theming, and mock data live in `commonMain` and are shared verbatim across the three targets. Only platform entry points (`MainActivity` on Android, `MainViewController` on iOS, `main()`/`index.html` on Web) and a handful of `expect`/`actual` declarations (e.g. status bar styling) are platform-specific.

## Project structure

```
POC/
├── Connectly/                 Kotlin Multiplatform project (the app itself)
│   ├── composeApp/            Shared module — all real source code
│   │   └── src/
│   │       ├── commonMain/    Shared UI, navigation, theme, data layer (all 3 targets)
│   │       │   └── kotlin/com/connectly/app/
│   │       │       ├── core/components/   Reusable Compose UI widgets
│   │       │       ├── core/theme/        Color, shape, type, Material theme
│   │       │       ├── data/model/        Plain data classes (Event, Person, UserProfile)
│   │       │       ├── data/repository/   In-memory/mock repositories
│   │       │       ├── feature/<name>/    One screen per feature package
│   │       │       └── navigation/        Nav graph, routes, scaffold
│   │       ├── androidMain/   MainActivity, AndroidManifest.xml, Android-only res/
│   │       ├── iosMain/       MainViewController.kt entry point, iOS actuals
│   │       └── wasmJsMain/    main.kt entry point, index.html, Web actuals
│   ├── iosApp/                Xcode project wrapping the composeApp iOS framework (macOS only)
│   ├── gradle/libs.versions.toml   Version catalog (Kotlin, AGP, Compose Multiplatform, deps)
│   ├── build.gradle.kts       Root project (plugin declarations only)
│   ├── settings.gradle.kts    Module list + repository configuration
│   └── local.properties.example   Copy to local.properties and set your own sdk.dir
└── docs/
    └── design-reference/      Original Stitch-generated mockups (HTML + screenshots) and
                                the executive_pulse color-token palette the theme was derived from
```

## Prerequisites

- **JDK 17 or newer** (JDK 21 is what this project is built/tested with). Point `JAVA_HOME` at it before running Gradle from the CLI if your system `java` resolves to an older version.
- **Android Studio** (a recent version with Kotlin Multiplatform / Compose Multiplatform plugin support) — for the Android Studio workflow.
- **A browser** to view the Web (Wasm) target (Chrome/Edge/Firefox — anything with a modern Wasm/GC implementation).
- **macOS + Xcode** — only required to build/run the iOS target. iOS **cannot be built, run, or verified from Windows or Linux**; the `iosMain` source set and `Connectly/iosApp` Xcode project are provided as scaffolding and can only be opened and built on a Mac.

### First-time setup

1. Copy `Connectly/local.properties.example` to `Connectly/local.properties` and set `sdk.dir` to your local Android SDK path (this file is gitignored and machine-specific — never commit it).
2. Open `Connectly/` in Android Studio and let Gradle sync, **or** use the CLI workflow below.

## Running

Run all commands from the `Connectly/` directory. On Windows, use `gradlew.bat`; on macOS/Linux, use `./gradlew`.

### Android Studio workflow

1. Open the `Connectly/` folder in Android Studio and let Gradle sync.
2. Select the `composeApp` run configuration with an Android target device/emulator.
3. Run ▶. This builds and installs the Android app.

### Android (CLI)

```bash
./gradlew :composeApp:assembleDebug     # build a debug APK
./gradlew :composeApp:installDebug      # build + install on a connected device/emulator
```

### Web / Kotlin-Wasm (CLI)

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun   # serves the app locally and opens it in a browser
./gradlew :composeApp:wasmJsBrowserDistribution      # produces a static production build
```

### iOS (macOS + Xcode only)

```bash
./gradlew :composeApp:compileKotlinIosSimulatorArm64   # compile-check the shared code for iOS (works on any OS)
```

The actual app can only be run on a Mac:

1. Open `Connectly/iosApp/iosApp.xcodeproj` in Xcode.
2. Select an iOS Simulator or device as the run target.
3. Run ▶. Xcode's build phase invokes `./gradlew :composeApp:embedAndSignAppleFrameworkForXcode` automatically to build and embed the shared framework before launching.

## Architecture

- **UI**: Jetpack/Compose Multiplatform, one `@Composable` screen per `feature/<name>` package, built from shared widgets in `core/components`.
- **Theming**: centralized in `core/theme` (`Color.kt`, `Shape.kt`, `Type.kt`, `Theme.kt`); derived from the token palette in `docs/design-reference/executive_pulse/DESIGN.md`.
- **Data**: `data/model` plain data classes + `data/repository` in-memory/mock repositories — no network or persistence layer yet; this is a UI-focused proof of concept.
- **Navigation**: single nav graph (`navigation/ConnectlyNavGraph.kt`) with route constants in `navigation/Routes.kt` and a shared `MainScaffold.kt` for the bottom-nav shell.
- **Platform entry points**: `androidMain`/`iosMain`/`wasmJsMain` each contain only what's platform-specific — activity/view-controller/HTML bootstrapping and a few `expect`/`actual` declarations (e.g. status bar styling).

## Testing

> **Status:** no automated tests exist yet. This is tracked as a gap, not a design decision.

Planned structure once added:
- `composeApp/src/commonTest/` — shared unit tests for repositories and any pure logic.
- `composeApp/src/androidUnitTest/` / `androidInstrumentedTest/` — Android-specific unit/instrumented tests.

Run (once tests exist):
```bash
./gradlew :composeApp:allTests          # all common/unit tests across targets
./gradlew :composeApp:testDebugUnitTest # Android unit tests only
```

## Continuous Integration

`.github/workflows/build.yml` runs on every push to `main` and on pull requests: it assembles the Android debug build, builds the Web/Wasm production distribution, and compile-checks the iOS shared code — the same three commands documented above, on `ubuntu-latest` with JDK 21 Temurin.

## Troubleshooting

- **`wasmJsBrowserDevelopmentRun` fails with "Build was configured to prefer settings repositories over project repositories but repository ... was added by unknown code"**: the Kotlin Gradle plugin registers its own repository at execution time to download the Node.js toolchain used by Kotlin/Wasm, which conflicts with `RepositoriesMode.FAIL_ON_PROJECT_REPOS` in `settings.gradle.kts`. Already fixed by using `RepositoriesMode.PREFER_PROJECT`.
- **Gradle/Kotlin HTTPS downloads fail with `PKIX path building failed: unable to find valid certification path`**: your network uses a TLS-inspecting corporate proxy (e.g. Zscaler) whose root CA is trusted by the OS/browser but not by the JDK's own `cacerts` truststore that Gradle uses. Import your organization's proxy root CA into the JDK truststore in use, or point Gradle at a truststore that already includes it.

## Known limitation

This project was developed and verified on Windows. The Android and Web (Wasm) targets have been built and smoke-tested end-to-end. The iOS target compiles from shared code (`compileKotlinIosSimulatorArm64` succeeds), and the `iosApp` Xcode project is provided as a standard Compose Multiplatform wrapper, but it has **not** been opened, built, or run in Xcode — that verification requires a Mac.
