package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun
import com.github.pflooky.datacaterer.api.model.{ArrayType, DateType, DecimalType, DoubleType, IntegerType, TimestampType}

import java.sql.{Date, Timestamp}

class JsonPlan extends PlanRun {

  val jsonTask = json("account_info", "/opt/app/data/json", Map("saveMode" -> "overwrite"))
    .schema(
      field.name("account_id").regex("ACC[0-9]{2}"),
      field.name("year").`type`(IntegerType).sql("YEAR(date)"),
      field.name("balance").`type`(DoubleType).min(10).max(1000),
      field.name("date").`type`(DateType).min(Date.valueOf("2022-01-01")),
      field.name("status").sql("element_at(sort_array(update_history, false), 1).status"),
      field.name("update_history")
        .`type`(ArrayType)
        .arrayMinLength(1)
        .schema(
          field.name("updated_time").`type`(TimestampType).min(Timestamp.valueOf("2022-01-01 00:00:00")),
          field.name("status").oneOf("open", "closed", "pending", "suspended"),
        ),
      field.name("customer_details")
        .schema(
          field.name("name").expression("#{Name.name}"),
          field.name("age").`type`(IntegerType).min(18).max(90),
          field.name("city").expression("#{Address.city}")
        )
    )
    .count(count.records(1000))

  val config = configuration
    .generatedReportsFolderPath("/opt/app/data/report")
    .enableUniqueCheck(true)

  execute(config, jsonTask)
}
