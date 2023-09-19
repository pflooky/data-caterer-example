package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun
import com.github.pflooky.datacaterer.api.model.{ArrayType, DateType, DoubleType}

import java.sql.Date

class ValidationPlanRun extends PlanRun {

  val jsonTask = json("my_json", "/opt/app/data/json")
    .schema(
      field.name("account_id").regex("ACC[0-9]{8}"),
      field.name("name").expression("#{Name.name}"),
      field.name("open_date").`type`(DateType).min(Date.valueOf("2022-01-01")),
      field.name("balance").`type`(DoubleType).min(100).max(10000),
      field.name("txn_list")
        .`type`(ArrayType)
        .schema(
          field.name("id"),
          field.name("date").`type`(DateType).min(Date.valueOf("2022-01-01")),
          field.name("amount").`type`(DoubleType),
        )
    )
    .validationWait(waitCondition.pause(2))
    .validations(
      validation.expr("LENGTH(name) > 3").description("Name should have more than 3 characters"),
      validation.expr("open_date IS NOT NULL").errorThreshold(10),
      validation.expr("balance > 500").errorThreshold(0.1),
    )

  execute(jsonTask)
}
