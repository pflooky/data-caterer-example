package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.api.model.DoubleType;
import com.github.pflooky.datacaterer.api.model.TimestampType;
import com.github.pflooky.datacaterer.java.api.PlanRun;

import java.util.Map;

public class CsvJavaPlan extends PlanRun {
    {
        var csvTask = csv("customer_accounts", "/opt/app/data/customer/account", Map.of("header", "true"))
                .schema(
                        field().name("account_id").regex("ACC[0-9]{8}").unique(true),
                        field().name("amount").type(DoubleType.instance()).min(1).max(1000),
                        field().name("created_by").sql("CASE WHEN status IN ('open', 'closed') THEN 'eod' ELSE 'event' END"),
                        field().name("name").expression("#{Name.name}"),
                        field().name("open_time").type(TimestampType.instance()).min(java.sql.Date.valueOf("2022-01-01")),
                        field().name("status").oneOf("open", "closed", "suspended", "pending")
                );

        var config = configuration()
                .generatedReportsFolderPath("/opt/app/data/report")
                .enableUniqueCheck(true);

        execute(config, csvTask);
    }
}
