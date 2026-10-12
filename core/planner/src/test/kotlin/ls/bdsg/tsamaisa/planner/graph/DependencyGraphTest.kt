package ls.bdsg.tsamaisa.planner.graph

import ls.bdsg.tsamaisa.planner.model.Dependency
import ls.bdsg.tsamaisa.planner.model.TaskId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

/**
 * Specification for Exercise 01. All of these fail until DependencyGraph is
 * implemented. Run with: ./gradlew :core:planner:test
 *
 * Don't edit a test to make it pass. If you think a test is wrong, that's a
 * design discussion worth having first.
 */
class DependencyGraphTest {

    // ---- helpers -----------------------------------------------------------

    private fun ids(vararg names: String) = names.map(::TaskId)
    private fun dep(before: String, after: String) = Dependency(TaskId(before), TaskId(after))

    private fun valid(tasks: List<TaskId>, deps: List<Dependency>): DependencyGraph {
        val result = DependencyGraph.build(tasks, deps)
        return assertIs<GraphBuildResult.Valid>(result, "expected a valid graph but got $result").graph
    }

    /** Checks the defining property of a topological order. */
    private fun assertRespectsDependencies(order: List<TaskId>, deps: List<Dependency>) {
        val position = order.withIndex().associate { (i, id) -> id to i }
        for (d in deps) {
            assertTrue(
                position.getValue(d.prerequisite) < position.getValue(d.dependent),
                "${d.prerequisite} must come before ${d.dependent} in $order",
            )
        }
    }

    // ---- building and validation -------------------------------------------

    @Test
    fun `empty input builds an empty graph`() {
        val graph = valid(emptyList(), emptyList())
        assertEquals(emptyList(), graph.topologicalOrder())
    }

    @Test
    fun `duplicate task ids are rejected`() {
        val result = DependencyGraph.build(ids("a", "b", "a"), emptyList())
        assertEquals(GraphBuildResult.DuplicateTask(TaskId("a")), result)
    }

    @Test
    fun `dependency on an unknown task is rejected`() {
        val bad = dep("a", "ghost")
        val result = DependencyGraph.build(ids("a", "b"), listOf(bad))
        assertEquals(GraphBuildResult.UnknownTask(bad, TaskId("ghost")), result)
    }

    @Test
    fun `repeated identical dependencies count once`() {
        val graph = valid(ids("a", "b"), listOf(dep("a", "b"), dep("a", "b")))
        assertEquals(setOf(TaskId("a")), graph.prerequisitesOf(TaskId("b")))
    }

    // ---- cycles ------------------------------------------------------------

    @Test
    fun `a task depending on itself is a cycle of length one`() {
        val result = DependencyGraph.build(ids("a", "b"), listOf(dep("a", "a")))
        assertEquals(GraphBuildResult.Cycle(ids("a")), result)
    }

    @Test
    fun `two tasks depending on each other form a cycle`() {
        val result = DependencyGraph.build(ids("a", "b"), listOf(dep("a", "b"), dep("b", "a")))
        val cycle = assertIs<GraphBuildResult.Cycle>(result)
        assertEquals(setOf(TaskId("a"), TaskId("b")), cycle.path.toSet())
        assertEquals(2, cycle.path.size)
    }

    @Test
    fun `cycle path lists exactly the tasks on the loop, in dependency order`() {
        // start -> x -> y -> z -> x   (start is NOT on the loop; neither is "other")
        val deps = listOf(dep("start", "x"), dep("x", "y"), dep("y", "z"), dep("z", "x"))
        val result = DependencyGraph.build(ids("start", "x", "y", "z", "other"), deps)
        val path = assertIs<GraphBuildResult.Cycle>(result).path

        assertEquals(setOf(TaskId("x"), TaskId("y"), TaskId("z")), path.toSet())
        assertEquals(3, path.size)
        // each element is a prerequisite of the next, wrapping around
        for (i in path.indices) {
            val from = path[i]
            val to = path[(i + 1) % path.size]
            assertTrue(Dependency(from, to) in deps, "$from -> $to is not a dependency")
        }
    }

    // ---- prerequisites -----------------------------------------------------

    @Test
    fun `prerequisitesOf returns direct prerequisites only`() {
        // a -> b -> c : c waits on b directly, not on a
        val graph = valid(ids("a", "b", "c"), listOf(dep("a", "b"), dep("b", "c")))
        assertEquals(setOf(TaskId("b")), graph.prerequisitesOf(TaskId("c")))
        assertEquals(emptySet(), graph.prerequisitesOf(TaskId("a")))
    }

    // ---- topological order -------------------------------------------------

    @Test
    fun `independent tasks keep their input order`() {
        val graph = valid(ids("c", "b", "a"), emptyList())
        assertEquals(ids("c", "b", "a"), graph.topologicalOrder())
    }

    @Test
    fun `a chain comes out in dependency order`() {
        // the song pipeline: beat -> hook -> record -> mix
        val tasks = ids("mix", "record", "beat", "hook")
        val deps = listOf(dep("beat", "hook"), dep("hook", "record"), dep("record", "mix"))
        assertEquals(ids("beat", "hook", "record", "mix"), valid(tasks, deps).topologicalOrder())
    }

    @Test
    fun `ties are broken by input order`() {
        // a waits on c. b and c are free first; b is earlier in the input.
        val graph = valid(ids("a", "b", "c"), listOf(dep("c", "a")))
        assertEquals(ids("b", "c", "a"), graph.topologicalOrder())
    }

    @Test
    fun `diamond dependencies`() {
        //    a
        //   / \
        //  b   c
        //   \ /
        //    d
        val deps = listOf(dep("a", "b"), dep("a", "c"), dep("b", "d"), dep("c", "d"))
        assertEquals(ids("a", "b", "c", "d"), valid(ids("a", "b", "c", "d"), deps).topologicalOrder())
    }

    @Test
    fun `larger graph respects every dependency and contains every task once`() {
        val tasks = ids("t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8")
        val deps = listOf(
            dep("t8", "t1"), dep("t7", "t1"), dep("t1", "t3"), dep("t2", "t3"),
            dep("t3", "t5"), dep("t4", "t5"), dep("t6", "t4"), dep("t8", "t6"),
        )
        val order = valid(tasks, deps).topologicalOrder()
        assertEquals(tasks.toSet(), order.toSet())
        assertEquals(tasks.size, order.size)
        assertRespectsDependencies(order, deps)
    }

    // ---- ready tasks -------------------------------------------------------

    @Test
    fun `with nothing completed, only tasks without prerequisites are ready`() {
        val graph = valid(ids("a", "b", "c"), listOf(dep("a", "c")))
        assertEquals(ids("a", "b"), graph.readyTasks(emptySet()))
    }

    @Test
    fun `completing a prerequisite unlocks its dependents`() {
        val graph = valid(ids("a", "b", "c"), listOf(dep("a", "c")))
        assertEquals(ids("b", "c"), graph.readyTasks(setOf(TaskId("a"))))
    }

    @Test
    fun `a task needs all of its prerequisites completed`() {
        val deps = listOf(dep("a", "c"), dep("b", "c"))
        val graph = valid(ids("a", "b", "c"), deps)
        assertEquals(ids("b"), graph.readyTasks(setOf(TaskId("a"))))
        assertEquals(ids("c"), graph.readyTasks(setOf(TaskId("a"), TaskId("b"))))
    }

    @Test
    fun `unknown ids in completed are ignored`() {
        val graph = valid(ids("a"), emptyList())
        assertEquals(ids("a"), graph.readyTasks(setOf(TaskId("not-in-graph"))))
    }
}
