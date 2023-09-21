package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun
import com.github.pflooky.datacaterer.api.model.{DoubleType, TimestampType}

class AdvancedCassandraPlanRun extends PlanRun {

  val accountTask = cassandra("customer_cassandra", "host.docker.internal:9042")
    .table("account", "accounts")
    .schema(
      field.name("account_id").regex("ACC[0-9]{8}").primaryKey(true),
      field.name("amount").`type`(DoubleType).min(1).max(1000),
      field.name("created_by").sql("CASE WHEN status IN ('open', 'closed') THEN 'eod' ELSE 'event'"),
      field.name("name").expression("#{Name.name}"),
      field.name("open_time").`type`(TimestampType),
      field.name("status").oneOf("open", "closed", "suspended", "pending")
    )

  val config = configuration
    .generatedReportsFolderPath("/opt/app/data/report")
    .enableUniqueCheck(true)

  execute(config, accountTask)
}
