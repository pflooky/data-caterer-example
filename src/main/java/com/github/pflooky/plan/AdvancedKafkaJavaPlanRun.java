package com.github.pflooky.plan;

import com.github.pflooky.datacaterer.api.connection.ConnectionTaskBuilder;
import com.github.pflooky.datacaterer.api.connection.KafkaBuilder;
import com.github.pflooky.datacaterer.api.model.ArrayType;
import com.github.pflooky.datacaterer.api.model.DateType;
import com.github.pflooky.datacaterer.api.model.DoubleType;
import com.github.pflooky.datacaterer.api.model.IntegerType;
import com.github.pflooky.datacaterer.api.model.TimestampType;
import com.github.pflooky.datacaterer.java.api.PlanRun;

import java.sql.Date;
import java.util.Map;

public class AdvancedKafkaJavaPlanRun extends PlanRun {
    {
        var kafkaTask = getKafkaTask();

        var conf = configuration()
                .generatedReportsFolderPath("/opt/app/data/report");

        execute(conf, kafkaTask);
    }

    public ConnectionTaskBuilder<KafkaBuilder> getKafkaTask() {
        return kafka("my_kafka", "kafkaserver:29092")
                .topic("account-topic")
                .schema(
                        field().name("key").sql("content.account_id"),
                        field().name("value").sql("TO_JSON(content)"),
                        //field().name("partition").type(IntegerType.instance()),  can define partition here
                        field().name("headers")
                                .type(ArrayType.instance())
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
                                ),
                        field().name("tmp_year").sql("content.year").omit(true),
                        field().name("tmp_name").sql("content.details.name").omit(true)
                )
                .count(count().records(100));
    }
}
