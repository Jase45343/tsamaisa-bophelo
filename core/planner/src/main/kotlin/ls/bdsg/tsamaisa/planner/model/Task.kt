package ls.bdsg.tsamaisa.planner.model

/**
 * Identifies a task. A value class wraps a String with no runtime cost, but
 * stops you from passing (say) a project id where a task id is expected.
 */
@JvmInline
value class TaskId(val value: String) {
    override fun toString(): String = value
}

enum class TaskStatus { TODO, IN_PROGRESS, BLOCKED, DEFERRED, DONE, CANCELLED }

/**
 * Deliberately minimal. More fields (due date, priority, links to
 * Project/Goal/Pillar) arrive with the data-model ADR, informed by the paper run.
 */
data class Task(
    val id: TaskId,
    val title: String,
    val estimateMinutes: Int? = null,
    val status: TaskStatus = TaskStatus.TODO,
)

/**
 * "[prerequisite] must be finished before [dependent] can start."
 * Example: Dependency(prerequisite = finishBeat, dependent = recordVocals)
 */
data class Dependency(
    val prerequisite: TaskId,
    val dependent: TaskId,
)
