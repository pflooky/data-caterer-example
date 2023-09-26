package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.java.api.PlanRun;

public class AdvancedDeleteJavaPlanRun extends PlanRun {
    {
        var autoRun = configuration()
                .postgres("my_postgres", "jdbc:postgresql://host.docker.internal:5432/customer")
                .enableGeneratePlanAndTasks(true)
                .enableRecordTracking(true)
                .enableDeleteGeneratedRecords(false)
                .enableUniqueCheck(true)
                .generatedPlanAndTaskFolderPath("/opt/app/data/generated")
                .recordTrackingFolderPath("/opt/app/data/recordTracking")
                .generatedReportsFolderPath("/opt/app/data/report");

        execute(autoRun);
    }
}
