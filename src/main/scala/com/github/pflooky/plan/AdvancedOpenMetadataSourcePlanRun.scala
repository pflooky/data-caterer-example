package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun
import com.github.pflooky.datacaterer.api.model.Constants.{OPEN_METADATA_AUTH_TYPE_OPEN_METADATA, OPEN_METADATA_JWT_TOKEN, OPEN_METADATA_TABLE_FQN}

class AdvancedOpenMetadataSourcePlanRun extends PlanRun {

  val jsonTask = json("my_json", "/opt/app/data/json", Map("saveMode" -> "overwrite"))
    .schema(metadataSource.openMetadata(
      "http://host.docker.internal:8585/api",
      OPEN_METADATA_AUTH_TYPE_OPEN_METADATA,
      Map(
        OPEN_METADATA_JWT_TOKEN -> "eyJraWQiOiJHYjM4OWEtOWY3Ni1nZGpzLWE5MmotMDI0MmJrOTQzNTYiLCJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJvcGVuLW1ldGFkYXRhLm9yZyIsInN1YiI6ImluZ2VzdGlvbi1ib3QiLCJlbWFpbCI6ImluZ2VzdGlvbi1ib3RAb3Blbm1ldGFkYXRhLm9yZyIsImlzQm90Ijp0cnVlLCJ0b2tlblR5cGUiOiJCT1QiLCJpYXQiOjE2OTcxNzc0MzgsImV4cCI6bnVsbH0.jnO65SJZG9GQuVlJvpyKrrBZejPpjV71crJEvWOMPyeozZkoEyYy-kcb8UkVenidDcoAdie4Zhl4saNyaLudiAO2MKhSU1Rf3yT2M3BQBf37kQ3Ma4pjrx-lXVk2SmCaHsgLFETksSHZTwgPrtx5L3d2FOCfF92dANI_tldTg5Jog51tjHyYWYV4y4_eU4AfC7gXjIhvU35vTJmzUWH7BUkDGfcwHnIVa0AOqLzwZUQT1S717yNoenj2CUTBNS4fxWlATWBQIMG9JaBmQAAYNWOFPKnVWfWGv7Ya1OEW5wtb7A69hyPAT1lS-_FIxOOMkGbdg2u3sFuu9rD1d2JMdg", //find under settings/bots/ingestion-bot/token
        OPEN_METADATA_TABLE_FQN -> "sample_data.ecommerce_db.shopify.raw_customer"
      )
    ))
    .schema(
      field.name("platform").oneOf("website", "mobile"),
      field.name("customer").schema(field.name("sex").oneOf("M", "F", "O"))
    )
    .count(count.records(10))

  val conf = configuration.enableGeneratePlanAndTasks(true)
    .enableGenerateValidations(true)
    .generatedReportsFolderPath("/opt/app/data/report")

  execute(conf, jsonTask)
}
