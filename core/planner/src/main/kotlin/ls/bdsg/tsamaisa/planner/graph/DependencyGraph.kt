package ls.bdsg.tsamaisa.planner.graph

import ls.bdsg.tsamaisa.planner.model.Dependency
import ls.bdsg.tsamaisa.planner.model.TaskId

/**
 * The outcome of trying to build a graph. Invalid input is returned as a value
 * rather than thrown as an exception, so the UI can show the user exactly what
 * is wrong ("these three tasks depend on each other in a loop").
 */
sealed interface GraphBuildResult {
    data class Valid(val graph: DependencyGraph) : GraphBuildResult

    /** The same task id appeared more than once in the task list. */
    data class DuplicateTask(val id: TaskId) : GraphBuildResult

    /** A dependency mentions a task that is not in the task list. */
    data class UnknownTask(val dependency: Dependency, val missing: TaskId) : GraphBuildResult

    /**
     * The dependencies contain a loop. [path] lists each task on the loop exactly
     * once, so that path[i] is a prerequisite of path[i + 1], and the last
     * element is a prerequisite of the first. A task depending on itself gives
     * a path of length 1.
     */
    data class Cycle(val path: List<TaskId>) : GraphBuildResult
}

/**
 * A validated, cycle-free set of tasks and the dependencies between them
 * (a directed acyclic graph, or DAG).
 *
 * EXERCISE 01 — implemented by ATL. See docs/exercises/01-dependency-graph.md.
 *
 * You may change the private constructor and internal representation however
 * you like. The tests only use the public API below.
 */
class DependencyGraph private constructor(
    /** Every task, in the order the caller supplied them. */
    val tasks: List<TaskId>,
    /** For each task: the tasks that must be finished before it. */
    private val prerequisites: Map<TaskId, Set<TaskId>>,
) {

    /** The direct prerequisites of [task]: the tasks it waits on. Empty if none. */
    fun prerequisitesOf(task: TaskId): Set<TaskId> =
        TODO("Exercise 01")

    /**
     * Every task, ordered so that each task comes after all of its prerequisites.
     *
     * Tie-break rule (makes the output predictable and testable): whenever
     * several tasks are free to go next, pick the one that appears earliest
     * in [tasks].
     */
    fun topologicalOrder(): List<TaskId> =
        TODO("Exercise 01")

    /**
     * Tasks that could be worked on now: not in [completed], and every one of
     * their prerequisites is in [completed]. Returned in [tasks] order.
     * Ids in [completed] that are not in this graph are ignored.
     */
    fun readyTasks(completed: Set<TaskId>): List<TaskId> =
        TODO("Exercise 01")

    companion object {
        /**
         * Validates the input and builds the graph. Checks, in this order:
         *  1. duplicate task ids        -> DuplicateTask
         *  2. unknown ids in a dependency -> UnknownTask
         *  3. loops                     -> Cycle
         * Repeated identical dependencies are allowed and count once.
         */
        fun build(tasks: List<TaskId>, dependencies: List<Dependency>): GraphBuildResult =
            TODO("Exercise 01")
    }
}
