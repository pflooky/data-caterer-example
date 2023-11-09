package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.api.model.ArrayType;
import com.github.pflooky.datacaterer.api.model.DateType;
import com.github.pflooky.datacaterer.api.model.DoubleType;
import com.github.pflooky.datacaterer.api.model.HeaderType;
import com.github.pflooky.datacaterer.api.model.IntegerType;
import com.github.pflooky.datacaterer.api.model.StringType;
import com.github.pflooky.datacaterer.api.model.StructType;
import com.github.pflooky.datacaterer.api.model.TimestampType;
import com.github.pflooky.datacaterer.java.api.PlanRun;
import scala.Tuple2;

import java.sql.Date;
import java.util.List;

public class AdvancedSolaceJavaPlanRun extends PlanRun {
    {
        var solaceTask = solace("my_solace", "smf://host.docker.internal:55554")
                .destination("/JNDI/Q/rest_test_queue")
                .schema(
                        field().name("value").sql("TO_JSON(content)"),
                        //field().name("partition").type(IntegerType.instance()),   //can define message JMS priority here
                        field().name("headers") //set message properties via headers field
                                .type(HeaderType.getType())
                                .sql(
                                        "ARRAY(" +
                                                "NAMED_STRUCT('key', 'account-id', 'value', TO_BINARY(content.account_id, 'utf-8'))," +
                                                "NAMED_STRUCT('key', 'updated', 'value', TO_BINARY(content.details.updated_by.time, 'utf-8'))" +
                                                ")"
                                ),
                        field().name("content")
                                .schema(
                                        field().name("account_id").regex("ACC[0-9]{8}"),
                                        field().name("year").type(IntegerType.instance()).min(2021).max(2023),
                                        field().name("amount").type(DoubleType.instance()),
                                        field().name("details")
                                                .schema(
                                                        field().name("name").expression("#{Name.name}"),
                                                        field().name("first_txn_date").type(DateType.instance()).sql("ELEMENT_AT(SORT_ARRAY(content.transactions.txn_date), 1)"),
                                                        field().name("updated_by")
                                                                .schema(
                                                                        field().name("user"),
                                                                        field().name("time").type(TimestampType.instance())
                                                                )
                                                ),
                                        field().name("transactions").type(ArrayType.instance())
                                                .schema(
                                                        field().name("txn_date").type(DateType.instance()).min(Date.valueOf("2021-01-01")).max("2021-12-31"),
                                                        field().name("amount").type(DoubleType.instance())
                                                )
                                )
                )
                .count(count().records(10));

        var conf = configuration()
                .generatedReportsFolderPath("/opt/app/data/report");

        execute(conf, solaceTask);
    }
}
