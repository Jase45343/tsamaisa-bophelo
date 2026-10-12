package ls.bdsg.tsamaisa.core.data

/*
 * :core:data — local storage and repositories.
 *
 * Responsibilities (once the data-model ADR is accepted):
 *  - Room database, entities and DAOs
 *  - Mapping between database entities and the domain types in :core:planner
 *  - Export to a documented format (SRS FR-122)
 *
 * Dependency rule: :core:data may depend on :core:planner, never the reverse.
 */
internal object DataModulePlaceholder
