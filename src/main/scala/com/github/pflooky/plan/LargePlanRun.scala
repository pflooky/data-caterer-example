package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun
import com.github.pflooky.datacaterer.api.model.{ArrayType, DateType, DoubleType}

import java.sql.Date

class LargePlanRun extends PlanRun {

  val jsonTask = json("my_json", "/opt/app/data/json")
    .schema(
      field.name("account_id").regex("ACC[0-9]{8}"),
      field.name("name").expression("#{Name.name}"),
      field.name("txn_list")
        .`type`(ArrayType)
        .schema(
          field.name("id"),
          field.name("date").`type`(DateType).min(Date.valueOf("2022-01-01")),
          field.name("amount").`type`(DoubleType),
        )
    )
    .count(count.records(10000000))

  val conf = configuration.numRecordsPerBatch(100000)
  execute(conf, jsonTask)
}
