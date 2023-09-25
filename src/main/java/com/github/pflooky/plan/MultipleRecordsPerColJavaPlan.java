package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.api.model.DateType;
import com.github.pflooky.datacaterer.api.model.DoubleType;
import com.github.pflooky.datacaterer.api.model.TimestampType;
import com.github.pflooky.datacaterer.java.api.PlanRun;

import java.util.Map;

public class MultipleRecordsPerColJavaPlan extends PlanRun {
    {
        var transactionTask = csv("customer_transactions", "/opt/app/data/customer/transaction", Map.of("header", "true"))
                .schema(
                        field().name("account_id").regex("ACC[0-9]{8}"),
                        field().name("full_name").expression("#{Name.name}"),
                        field().name("amount").type(DoubleType.instance()).min(1).max(100),
                        field().name("time").type(TimestampType.instance()).min(java.sql.Date.valueOf("2022-01-01")),
                        field().name("date").type(DateType.instance()).sql("DATE(time)")
                )
                .count(count().recordsPerColumnGenerator(generator().min(0).max(5), "account_id", "full_name"));

        var config = configuration()
                .generatedReportsFolderPath("/opt/app/data/report")
                .enableUniqueCheck(true);

        execute(config, transactionTask);
    }
}
