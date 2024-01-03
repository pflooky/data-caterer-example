package io.github.datacatering.plan

import io.github.datacatering.datacaterer.api.PlanRun

class JsonPlan extends PlanRun {

  val jsonTask = json("account_info", "/opt/app/data/json", Map("saveMode" -> "overwrite"))
    .schema(new ValidationPlanRun().baseSchema)
    .count(count.records(1000))

  val config = configuration
    .generatedReportsFolderPath("/opt/app/data/report")
    .enableUniqueCheck(true)

  execute(config, jsonTask)
}
