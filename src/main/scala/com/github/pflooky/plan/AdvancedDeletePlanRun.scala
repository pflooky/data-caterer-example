package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun

/**
 * Generate data for all tables in Postgres
 */
class AdvancedDeletePlanRun extends PlanRun {

  val autoRun = configuration
    .postgres("my_postres", "jdbc:postgresql://host.docker.internal:5432/customer")
    .enableGeneratePlanAndTasks(true)
    .enableRecordTracking(true)
    .enableDeleteGeneratedRecords(false)
    .enableUniqueCheck(true)
    .generatedPlanAndTaskFolderPath("/opt/app/data/generated")
    .recordTrackingFolderPath("/opt/app/data/recordTracking")
    .generatedReportsFolderPath("/opt/app/data/report")
    .enableGenerateData(true)

  execute(configuration = autoRun)
}
