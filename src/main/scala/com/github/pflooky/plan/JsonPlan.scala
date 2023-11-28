package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun
import com.github.pflooky.datacaterer.api.model.{ArrayType, DateType, DecimalType, DoubleType, IntegerType, TimestampType}

import java.sql.{Date, Timestamp}

class JsonPlan extends PlanRun {

  val jsonTask = json("account_info", "/opt/app/data/json", Map("saveMode" -> "overwrite"))
    .schema(new ValidationPlanRun().baseSchema)
    .count(count.records(1000))

  val config = configuration
    .generatedReportsFolderPath("/opt/app/data/report")
    .enableUniqueCheck(true)

  execute(config, jsonTask)
}
