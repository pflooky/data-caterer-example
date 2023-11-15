package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.java.api.PlanRun;

public class ValidationJavaPlan extends PlanRun {
    {
        var jsonTask = json("my_json", "/opt/app/data/json")
                .validations(
                        validation().col("customer_details.name").matches("[A-Z][a-z]+ [A-Z][a-z]+").errorThreshold(0.1).description("Names generally follow the same pattern"),
                        validation().col("date").isNotNull().errorThreshold(10),
                        validation().col("balance").greaterThan(500),
                        validation().expr("YEAR(date) == year"),
                        validation().col("status").in("open", "closed", "pending").errorThreshold(0.2).description("Could be new status introduced"),
                        validation().col("customer_details.age").greaterThan(18),
                        validation().expr("FORALL(update_history, x -> x.updated_time > TIMESTAMP('2022-01-01 00:00:00'))"),
                        validation().col("update_history").greaterThanSize(2),
                        validation().unique("account_id"),
                        validation().groupBy().count().isEqual(1000),
                        validation().groupBy("account_id").max("balance").lessThan(900)
                );

        var config = configuration()
                .generatedReportsFolderPath("/opt/app/data/report")
                .enableValidation(true)
                .enableGenerateData(false);

        execute(config, jsonTask);
    }
}
