package io.github.datacatering.plan

import io.github.datacatering.datacaterer.api.PlanRun
import io.github.datacatering.datacaterer.api.model.{ArrayType, DateType, DoubleType, IntegerType}

import java.sql.Date

class MultipleDataSourcePlanRun extends PlanRun {

  val startDate = Date.valueOf("2022-01-01")
  val accountIdField = field.name("account_id").regex("ACC[0-9]{8}")
  val nameField = field.name("name").expression("#{Name.name}")

  val postgresTask = task.name("customer_postgres")
    .steps(
      step
        .name("transaction")
        .jdbcTable("account.transaction")
        .schema(
          accountIdField,
          field.name("txn_id").regex("txn_[0-9]{5}"),
          field.name("year").`type`(IntegerType).sql("YEAR(date)"),
          nameField,
          field.name("date").`type`(DateType).min(startDate),
          field.name("amount").`type`(DoubleType).max(10000),
          field.name("credit_debit").sql("CASE WHEN amount < 0 THEN 'C' ELSE 'D' END"),
        ),
      step
        .name("account")
        .jdbcTable("account.account")
        .schema(
          accountIdField,
          nameField,
          field.name("open_date").`type`(DateType).min(startDate),
          field.name("status").oneOf("open", "closed", "pending")
        )
    )

  val jsonTask = task.name("account_json")
    .steps(
      step
        .name("account_info")
        .path("src/main/resources/sample/json")
        .schema(
          accountIdField,
          nameField,
          field.name("txn_list")
            .`type`(ArrayType)
            .schema(
              field.name("id"),
              field.name("date").`type`(DateType).min(startDate),
              field.name("amount").`type`(DoubleType),
            )
        )
    )

  execute()
}
