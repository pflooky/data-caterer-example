package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun

/**
 * Generate data for all tables in Postgres with foreign key relationships maintained
 */
class AdvancedAutomatedPlanRun extends PlanRun {

  val autoRun = configuration
    .postgres("my_postgres", "jdbc:postgresql://postgresserver:5432/customer")
    .enableGeneratePlanAndTasks(true)

  execute(configuration = autoRun)
}

/**
 * Generate data in Postgres only for schema 'account' and table 'accounts'
 */
class AdvancedAutomatedWithFilterPlanRun extends PlanRun {

  val autoRun = configuration
    .postgres(
      "my_postgres",
      "jdbc:postgresql://host.docker.internal:5432/customer",
    )
    .enableGeneratePlanAndTasks(true)
    .enableUniqueCheck(true)

  execute(configuration = autoRun)
}
