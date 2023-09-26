package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun
import com.github.pflooky.datacaterer.api.connection.{ConnectionTaskBuilder, FileBuilder}

class AdvancedBatchEventPlanRun extends PlanRun {

  val kafkaTask = new AdvancedKafkaPlanRun().kafkaTask

  val csvTask: ConnectionTaskBuilder[FileBuilder] = csv("my_csv", "/opt/app/data/csv/account")
    .schema(
      field.name("account_number"),
      field.name("year"),
      field.name("name"),
      field.name("payload")
    )

  val myPlan = plan.addForeignKeyRelationship(
    kafkaTask, List("key", "tmp_year", "tmp_name", "value"),
    List(csvTask -> List("account_number", "year", "name", "payload"))
  )

  val conf = configuration.generatedReportsFolderPath("/opt/app/data/report")

  execute(myPlan, conf, kafkaTask, csvTask)
}
