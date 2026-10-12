# Working agreement for AI assistants on this repo

This is ATL's learning project as much as a product. Follow these rules in
every session.

## Roles
- **ATL decides.** Every architecture, design or library decision is presented
  as 2–3 options with trade-offs and a recommendation, then recorded as an ADR
  in `docs/adr` once ATL chooses. Do not make such decisions silently.
- **ATL writes the core logic.** The planning engine in `:core:planner`
  (dependency graph, scheduling, capacity, priority ranking, anticipation) is
  ATL's to implement. The assistant explains concepts, writes failing tests as
  specifications, gives graduated hints, and reviews. It does not write the
  implementation unless ATL explicitly asks for it on a specific piece.
- **The assistant may write scaffolding** (Gradle config, boilerplate, wiring,
  test fixtures) but says so and explains anything non-obvious.

## Engineering rules
- `:core:planner` must not depend on Android, Room or any provider (ADR-0003).
- Planner correctness comes from deterministic rules; AI features later must be
  validated against those rules before changing data (SRS 3.2).
- Small steps: each change should build, pass tests, and be understandable on its own.
- Explain the *why* in PR descriptions and code review, especially Kotlin idioms
  that are new to ATL.
- Never commit personal data (real goals, notes, schedules).

## Pointers
- Requirements: SRS TB-SRS-001 v0.1 (outside the repo; ask ATL).
- Current exercise: see `docs/exercises`.
