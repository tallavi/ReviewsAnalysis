package com.tallavi.roundforest1.utils;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;

import java.util.List;

/**
 * Created by tallavi on 26/06/2017.
 */
public class SparkUtils {

    public static Dataset<Row> loadDataSet(SparkSession sparkSession, String path) {

        return sparkSession.read().
                format("com.databricks.spark.csv").
                option("header","true").
                option("inferSchema","true").
                load(path);
    }

    public static List<String> collectColumnToList(Dataset<Row> dataset, String columnName) {
        return dataset.map((MapFunction<Row, String>) row -> row.getString(row.fieldIndex(columnName)), Encoders.STRING()).collectAsList();
    }
}
