package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.java.api.PlanRun;

import java.util.Map;

public class AdvancedAutomatedJavaPlanRun extends PlanRun {
    {
        var autoRun = configuration()
                .postgres("my_postgres", "jdbc:postgresql://host.docker.internal:5432/customer")
                .enableGeneratePlanAndTasks(true)
                .generatedPlanAndTaskFolderPath("/opt/app/data/generated")
                .enableUniqueCheck(true)
                .generatedReportsFolderPath("/opt/app/data/report");

        execute(autoRun);
    }
}
