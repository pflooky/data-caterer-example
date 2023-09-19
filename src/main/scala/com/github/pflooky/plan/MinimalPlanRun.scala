package com.github.pflooky.plan

import com.github.pflooky.datacaterer.api.PlanRun

/**
 * Minimal plan run
 * It will do the following:
 * 1. Generate metadata from CSV data source
 * 2. Generate 1000 records based off the metadata
 * 3. Create HTML report
 */
class MinimalPlanRun extends PlanRun {
  execute(configuration = configuration
    .enableGeneratePlanAndTasks(true)
    .csv("transactions", "src/main/resources/sample/csv"))
}
