package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.api.model.Constants;
import com.github.pflooky.datacaterer.java.api.PlanRun;

import java.util.Map;

public class AdvancedOpenMetadataSourceJavaPlanRun extends PlanRun {
    {
        var jsonTask = json("my_json", "/opt/app/data/json", Map.of("saveMode", "overwrite"))
                .schema(metadataSource().openMetadataJava(
                        "http://host.docker.internal:5001",
                        Constants.OPEN_METADATA_AUTH_TYPE_OPEN_METADATA(),
                        Map.of(
                                Constants.OPEN_METADATA_JWT_TOKEN(), "abc123",
                                Constants.OPEN_METADATA_TABLE_FQN(), "sample_data.ecommerce_db.shopify.raw_customer"
                        )
                ))
                .schema(
                        field().name("platform").oneOf("website", "mobile"),
                        field().name("customer").schema(field().name("sex").oneOf("M", "F", "O"))
                )
                .count(count().records(10));

        var conf = configuration().enableGeneratePlanAndTasks(true)
                .enableGenerateValidations(true)
                .generatedReportsFolderPath("/opt/app/data/report");

        execute(conf, jsonTask);
    }
}
