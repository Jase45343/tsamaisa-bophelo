# ADR-0003: Module structure

Status: Proposed (awaiting ATL)
Date: 2026-10-13
Decided by: ATL

## Context
The planning engine is the product's core intellectual property and its most
algorithmic part. It must be easy to test thoroughly and must not depend on
Android, the database or any external provider (SRS 3.2, NFR-007).

## Options
- A. Single `:app` module.
- B. `:app` + `:core:planner` (pure Kotlin) + `:core:data` (Room).
- C. Full feature modularisation (`:feature:today`, `:feature:pipelines`, ...).

## Decision
B (proposed).

```
:app  ──> :core:data ──> :core:planner
  └──────────────────────────┘
```

Dependency rules:
- `:core:planner` depends on nothing but the Kotlin standard library.
- `:core:data` may depend on `:core:planner`, never the reverse.
- `:app` (Compose UI + ViewModels) may depend on both.

## Consequences
- Planner tests run on the JVM in milliseconds, no emulator needed.
- Domain types live in `:core:planner`; Room entities in `:core:data` are
  mapped to them, so the database schema can change without touching the planner.
- Slightly more Gradle configuration than option A.
- Can be split further into feature modules later if needed.
