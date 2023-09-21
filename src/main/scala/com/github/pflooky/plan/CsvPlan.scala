package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun
import com.github.pflooky.datacaterer.api.model.{DoubleType, TimestampType}

class CsvPlan extends PlanRun {

  val csvTask = csv("customer_accounts", "/opt/app/data/customer/account", Map("header" -> "true"))
    .schema(
      field.name("account_id").regex("ACC[0-9]{8}").unique(true),
      field.name("amount").`type`(DoubleType).min(1).max(1000),
      field.name("created_by").sql("CASE WHEN status IN ('open', 'closed') THEN 'eod' ELSE 'event' END"),
      field.name("name").expression("#{Name.name}"),
      field.name("open_time").`type`(TimestampType).min(java.sql.Date.valueOf("2022-01-01")),
      field.name("status").oneOf("open", "closed", "suspended", "pending")
    )

  val config = configuration
    .generatedReportsFolderPath("/opt/app/data/report")
    .enableUniqueCheck(true)

  execute(config, csvTask)
}
