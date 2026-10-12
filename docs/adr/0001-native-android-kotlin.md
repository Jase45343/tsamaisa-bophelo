# ADR-0001: Native Android with Kotlin

Status: Accepted
Date: 2026-10-12
Decided by: ATL

## Context
Two signature features depend on deep platform access: proactive reminders
(exact alarms that survive reboot and Samsung battery optimisation) and focus
mode (accessibility service / usage access). The MVP is Android-only and
offline-first. The project is also a learning vehicle for its owner.

## Options
- A. Kotlin + Jetpack Compose + Room (native).
- B. React Native/Expo + TypeScript (builds on existing TS knowledge).

## Decision
A. The features that differentiate the product live closest to the platform.

## Consequences
- Full access to platform APIs without bridge code.
- One language across UI, persistence and the planning engine.
- Learning cost: Kotlin, Compose and coroutines are new.
- iOS is out of scope for the foreseeable future.
