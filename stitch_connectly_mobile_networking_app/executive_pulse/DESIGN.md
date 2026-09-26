---
name: Executive Pulse
colors:
  surface: '#faf8ff'
  surface-dim: '#d2d9f4'
  surface-bright: '#faf8ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f2f3ff'
  surface-container: '#eaedff'
  surface-container-high: '#e2e7ff'
  surface-container-highest: '#dae2fd'
  on-surface: '#131b2e'
  on-surface-variant: '#464554'
  inverse-surface: '#283044'
  inverse-on-surface: '#eef0ff'
  outline: '#777586'
  outline-variant: '#c7c4d7'
  surface-tint: '#5148d7'
  primary: '#2a14b4'
  on-primary: '#ffffff'
  primary-container: '#4338ca'
  on-primary-container: '#c1beff'
  inverse-primary: '#c3c0ff'
  secondary: '#006c49'
  on-secondary: '#ffffff'
  secondary-container: '#6cf8bb'
  on-secondary-container: '#00714d'
  tertiary: '#692400'
  on-tertiary: '#ffffff'
  tertiary-container: '#8f3400'
  on-tertiary-container: '#ffb393'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#e3dfff'
  primary-fixed-dim: '#c3c0ff'
  on-primary-fixed: '#100069'
  on-primary-fixed-variant: '#372abf'
  secondary-fixed: '#6ffbbe'
  secondary-fixed-dim: '#4edea3'
  on-secondary-fixed: '#002113'
  on-secondary-fixed-variant: '#005236'
  tertiary-fixed: '#ffdbcd'
  tertiary-fixed-dim: '#ffb597'
  on-tertiary-fixed: '#360f00'
  on-tertiary-fixed-variant: '#7d2d00'
  background: '#faf8ff'
  on-background: '#131b2e'
  surface-variant: '#dae2fd'
typography:
  headline-xl:
    fontFamily: Inter
    fontSize: 36px
    fontWeight: '800'
    lineHeight: 44px
    letterSpacing: -0.03em
  headline-xl-mobile:
    fontFamily: Inter
    fontSize: 30px
    fontWeight: '800'
    lineHeight: 38px
    letterSpacing: -0.025em
  headline-lg:
    fontFamily: Inter
    fontSize: 26px
    fontWeight: '700'
    lineHeight: 34px
    letterSpacing: -0.02em
  headline-sm:
    fontFamily: Inter
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
    letterSpacing: -0.015em
  subheading-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '500'
    lineHeight: 24px
    letterSpacing: -0.01em
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: Inter
    fontSize: 13px
    fontWeight: '600'
    lineHeight: 18px
    letterSpacing: 0.01em
  label-sm:
    fontFamily: Inter
    fontSize: 11px
    fontWeight: '600'
    lineHeight: 14px
    letterSpacing: 0.04em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  margin: 1.25rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style
The design system reflects high-trust executive mobility, precision networking, and effortless professional connectivity. It sits at the intersection of Modern Corporate and Tactile Minimalism—designed to feel as polished and deliberate as high-end hardware, yet warm and frictionless in high-stakes professional exchanges.

The target audience consists of founders, enterprise leaders, investors, and elite operators who prioritize immediate clarity, prestige, and speed. The interface avoids clinical corporate dryness and social media noise, instead cultivating an aura of quiet authority, discretion, and forward momentum. Interactions must feel instantaneous, tactile, and definitive, reinforcing the feeling that every contact, insight, and exchange is valuable.

## Colors
The palette is rooted in an ultra-crisp, high-contrast light mode that guarantees daylight legibility during conferences and dynamic meeting settings:

- **Primary Canvas & Surfaces**: The base application canvas utilizes `#F8F9FA` to soften optical glare while preserving absolute cleanliness. Interactive cards, sheets, and elevated overlays rely on pure `#FFFFFF` to distinguish structural modules from the base canvas.
- **Primary Indigo (`#4338CA`)**: A deep, electric indigo used selectively for high-intent actions, key brand moments, and primary interactive focal points. It communicates institutional trust combined with digital-native velocity.
- **Secondary Accent (`#10B981`)**: An authoritative emerald green reserved specifically for verified credentials, mutual connection confirmations, active availability pulses, and affirmative status indicators.
- **Text & Foreground**: Primary copy uses deep charcoal slate (`#0F172A`) for maximum optical contrast and punch without the harshness of pure black. Secondary and metadata content leverages neutral cool slate (`#64748B`), preserving a clean hierarchy.
- **Structural Dividers**: Outlines and borders utilize `#E2E8F0` and `#F1F5F9`, enforcing crisp spatial boundaries without competing with typography.

## Typography
Typography is driven by a singular, precision-tuned sans-serif system utilizing Inter across all screen levels. To convey status and architectural rigor, display headlines leverage heavy weights (`700` and `800`) paired with aggressive negative tracking (`-0.02em` to `-0.03em`). 

Subheadings and metadata prioritize rapid scanning. Numerical counters, tags, and categorical badges utilize compact, uppercase or tight tracking settings to guarantee distinction against running body text. Microcopy and utility indicators remain strictly at or above 11px to maintain legibility in mobile operating conditions.

## Layout & Spacing
The layout architecture is optimized for modern mobile touch ergonomics (modeled around standard 390x844pt viewports). 

- **Grid Model**: A fluid 4-column layout on mobile devices with a standard 20px (`1.25rem`) side canvas margin and a 16px (`1rem`) gutter between elements.
- **Vertical Rhythm**: Built on a modular 4px base increment. Touch targets conform to a minimum 48px baseline for critical interactive regions, with standard bottom navigation and floating card actions anchored within thumb-reachable zones.
- **Screen Boundaries**: Dynamic bottom safe-area insets must always feature a minimum `1.5rem` (`24px`) clear buffer between actionable buttons and device home indicators.

## Elevation & Depth
Depth in the design system is achieved through subtle structural containment rather than heavy drop shadows:

- **Flat Layering & Hairlines**: Primary depth is defined through the contrast of pure white `#FFFFFF` panels resting over the `#F8F9FA` background, bordered by a single-pixel outline of `#E2E8F0` or `#F1F5F9`.
- **Soft Ambient Underglow**: For elevated states (such as active QR modal sheets and primary bottom action bars), use an ultra-diffused shadow tinted with neutral slate: `box-shadow: 0 10px 30px -10px rgba(15, 23, 42, 0.06), 0 4px 6px -2px rgba(15, 23, 42, 0.03)`.
- **Active Focus States**: Tapping or pressing cards yields a brief optical scale down (`transform: scale(0.985)`) coupled with a subtle darkening of the structural border to `#CBD5E1`.

## Shapes
The shape system uses refined curvature to balance tactile comfort with structured authority:

- **Cards & Primary Modules**: Standard cards and modal bottom sheets utilize `16px` to `24px` corner radii (`rounded-lg` to `rounded-xl`), creating distinct, floating containers.
- **Action Buttons & Chips**: Buttons utilize either a `12px` softened corner or a full pill silhouette (`9999px`) for contextual quick-tags and status chips.
- **Avatars & Media**: Avatars and verification glyphs follow continuous geometric rules—standard avatars are strictly circular (`full`), while interactive QR modules use `20px` internal corner radii to harmonize with the parent sheet.

## Components

### Buttons
- **Primary Action**: Electric Indigo (`#4338CA`) background with white typography, min-height 52px, corner radius 12px or pill, with subtle press state transitions.
- **Secondary / Outline**: `#FFFFFF` background with a 1px border of `#E2E8F0`, typography in `#0F172A`.
- **Ghost Action**: Transparent background with `#4338CA` or `#64748B` typography, designed for tertiary navigation items.

### Cards & Surfaces
- Surfaces are constructed from `#FFFFFF`, enclosed with a 1px solid border `#E2E8F0`, with `1.25rem` internal padding. Corner radius scales between 16px and 20px. 
- High-priority connection cards feature a subtle top accent or a dedicated slot for mutual-connection badges.

### Avatar & Identity Modules
- Standard avatar scales are fixed to **48px** (standard list items) and **56px** (profile headers and hero cards).
- All avatars include a 2px inner or outer ring border in `#FFFFFF` to ensure separation from rich backgrounds or overlapping stack patterns.
- An absolute-positioned 12px status dot in Emerald Green (`#10B981`) anchors to the bottom-right corner to indicate active availability.

### Prominent QR Code Component
- Centered on a dedicated high-elevation `#FFFFFF` card with 24px corner radius and 24px internal padding.
- Surrounded by an optical scanning boundary with subtle alignment marks at each corner.
- Includes quick-share utility buttons directly below the matrix with high-contrast icon-only pills.

### Chips & Badges
- **Status Badges**: Emerald Green tint (`#ECFDF5`) background with `#065F46` text, displaying verification marks and connection confirmations.
- **Filter Chips**: Pill-shaped containers (`9999px`), 32px height, 12px horizontal padding, `#F1F5F9` background when inactive, transitioning to `#0F172A` with white text when selected.

### Input Fields
- Enclosed inputs with `#FFFFFF` background, 1px `#E2E8F0` border, 48px height, and 12px corner radius. Focused state introduces a 1.5px `#4338CA` border with a subtle indigo ambient aura. Placeholder copy set in `#94A3B8`.