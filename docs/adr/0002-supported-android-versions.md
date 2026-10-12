# ADR-0002: Supported Android versions

Status: Proposed (awaiting ATL)
Date: 2026-10-13
Decided by: ATL

## Context
The reference device is a Samsung Galaxy S24 on Android 16 (API 36), One UI 8.5.
The SRS anticipates other users later, many on mid-range phones a few years old.

## Options
- A. minSdk 36: only Android 16. No compatibility code; almost nobody else can install.
- B. minSdk 31 (Android 12): most phones from recent years. Material You dynamic
  colour and the modern exact-alarm permission model both begin at 31, so the
  compatibility cost is very small.
- C. minSdk 26 (Android 8): nearly every phone in use; real workaround code for
  alarms, notifications and theming.

## Decision
B (proposed): compileSdk = targetSdk = 36, minSdk = 31.

## Consequences
- Dynamic colour can be used without version checks (see `ui/theme/Theme.kt`).
- Exact alarms follow the API 31+ permission flow only.
- Samsung "Never sleeping apps" setup must be part of reminder onboarding,
  and tested on the S24.
