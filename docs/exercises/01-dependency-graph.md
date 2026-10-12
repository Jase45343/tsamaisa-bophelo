# Exercise 01: The dependency graph

**File:** `core/planner/src/main/kotlin/ls/bdsg/tsamaisa/planner/graph/DependencyGraph.kt`
**Tests:** `core/planner/src/test/kotlin/ls/bdsg/tsamaisa/planner/graph/DependencyGraphTest.kt`
**Run:** `./gradlew :core:planner:test` (or the green arrow next to the test class in Android Studio)
**SRS:** FR-017, FR-043, data rule 7.2 ("dependency cycles must be rejected")

## Why this comes first
"Record vocals" cannot happen before "finish the beat". Every scheduling feature
(daily plan, backward planning from a deadline, the Blocked queue, deadline
risk) relies on knowing what is ready to do and in what order.

## The maths
Tasks are vertices; a dependency `prerequisite -> dependent` is a directed edge.
A valid set of dependencies is a **directed acyclic graph (DAG)**.

A **topological order** is a linear ordering of vertices where every edge goes
forward. It exists if and only if the graph has no cycle. (Worth proving to
yourself: one direction is immediate; for the other, a finite DAG always has a
vertex with in-degree 0.)

## What to implement
1. `build`: validate (duplicates, unknown ids, cycles) and construct the graph.
2. `prerequisitesOf`
3. `topologicalOrder`, with the tie-break rule in the KDoc.
4. `readyTasks`

Suggested order: make the validation tests pass first, then the rest.
Commit each time a group of tests turns green.

## Hints (read only as far as you need)

<details><summary>Hint 1: representation</summary>

Store, for each task, the set of its prerequisites. For cycle detection you
will probably also want the reverse direction: for each task, the tasks that
depend on it. `associateWith`, `getValue` and `mutableSetOf` are your friends.
</details>

<details><summary>Hint 2: topological order</summary>

Kahn's algorithm: repeatedly take a task with no unplaced prerequisites, place
it, and remove it from consideration. The tie-break rule tells you which
one to take when there are several.
</details>

<details><summary>Hint 3: finding a cycle and its path</summary>

Depth-first search with three colours: unvisited, "on the current path", and
finished. Reaching a task that is already on the current path means you have
found a loop. The loop is the part of the current path from that task onward.
Keep the current path in a list as you recurse.
</details>

<details><summary>Hint 4: Kotlin idioms you may want</summary>

`for (t in tasks) if (!seen.add(t)) return ...`: `add` returns false if
the element was already present. `?.let { return it }` is a neat early return
out of a loop. A local `fun` inside `build` can capture variables for recursion.
</details>

## Stretch (once everything is green)
- What is the time complexity of your `topologicalOrder` in terms of V tasks and
  E dependencies? Can you reach O((V + E) log V) while keeping the tie-break rule?
  (Think priority queue keyed on input position.)
- Add a test of your own for a case the suite misses.

## When you're done
Push your branch and ask for a review. We'll go through correctness, Kotlin
style and complexity together.
