package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.java.api.PlanRun;

public class AdvancedHttpJavaPlanRun extends PlanRun {
    {
        var httpTask = http("my_http")
                .schema(metadataSource().openApi("/opt/app/mount/http/petstore.json"))
                .schema(field().name("bodyContent").schema(field().name("id").regex("ID[0-9]{8}")))
                .count(count().records(2));

        var myPlan = plan().addForeignKeyRelationship(
                foreignField("my_http", "POST/pets", "bodyContent.id"),
                foreignField("my_http", "GET/pets/{id}", "pathParamid"),
                foreignField("my_http", "DELETE/pets/{id}", "pathParamid")
        );

        var conf = configuration().enableGeneratePlanAndTasks(true)
                .generatedReportsFolderPath("/opt/app/data/report");

        execute(myPlan, conf, httpTask);
    }
}
