# Tsamaisa Bophelo

*Your life has direction. Your days should reflect it.*

A personal operating system for intentional living: it connects a person's
Pillars (what matters), Pipelines (how ambitions become action) and Vaults
(knowledge) to a realistic, explainable daily plan.

**Status:** MVP 0.1 in development. Android-first, offline-first.

## Project layout

| Module | What it holds | Depends on |
|--------|---------------|------------|
| `:app` | Jetpack Compose UI, ViewModels | `:core:data`, `:core:planner` |
| `:core:data` | Local storage (Room), repositories, export | `:core:planner` |
| `:core:planner` | Domain model and planning engine, pure Kotlin | — |

See [docs/adr](docs/adr) for why things are the way they are.

## Getting started

Requirements: Android Studio (recent stable), its bundled JDK 21, and a device
on Android 12+ (reference device: Galaxy S24, Android 16).

1. Clone the repo and open the folder in Android Studio. Let Gradle sync.
   Android Studio may suggest newer dependency versions; accepting is fine.
2. Run the planner tests: `./gradlew :core:planner:test`
3. Run the app on your phone: enable Developer options and USB debugging,
   connect, and press Run.

## Working on it

- Decisions → an ADR in `docs/adr` (see the template).
- Learning exercises → `docs/exercises`. Start with
  [Exercise 01](docs/exercises/01-dependency-graph.md).
- Branch per change (`feat/…`, `fix/…`, `docs/…`) and open a pull request into `main`.
- Never commit personal data (real goals, notes, schedules) to this repository.

## Reference

The product requirements live in the SRS (TB-SRS-001 v0.1), kept outside the repo.
