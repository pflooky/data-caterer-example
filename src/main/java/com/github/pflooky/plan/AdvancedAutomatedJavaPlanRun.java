package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.java.api.PlanRun;

public class AdvancedAutomatedJavaPlanRun extends PlanRun {
    {
        var autoRun = configuration()
                .postgres("my_postgres", "jdbc:postgresql://postgresserver:5432/customer")
                .enableGeneratePlanAndTasks(true);

        execute(autoRun);
    }
}
