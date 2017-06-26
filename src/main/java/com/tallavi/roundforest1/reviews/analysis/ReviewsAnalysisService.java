package com.tallavi.roundforest1.reviews.analysis;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.*;
import com.tallavi.roundforest1.utils.SparkUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tallavi on 26/06/2017.
 */
public class ReviewsAnalysisService {

    private static final int MOST_POPULAR_PRODUCTS_LIMIT = 1000;
    private static final int MOST_ACTIVE_USERS_LIMIT = 1000;
    private static final int MOST_USED_WORDS_LIMIT = 1000;

    private SparkSession sparkSession;

    public ReviewsAnalysisService(SparkSession sparkSession) {

        this.sparkSession = sparkSession;
    }

    public ReviewsAnalysisResult analyze(String path) {

        ReviewsAnalysisResult result = new ReviewsAnalysisResult();

        Dataset<Row> dataSet = SparkUtils.loadDataSet(sparkSession, path);

        result.setMostPopularProducts(getMostPopularProducts(dataSet));

        result.setMostActiveUsers(getMostActiveUsers(dataSet));

        result.setMostUsedWords(getMostUsedWords(dataSet));

        return result;
    }

    private List<String> getMostPopularProducts(Dataset<Row> dataSet) {

        Dataset<Row> mostPopularProducts = dataSet.
                groupBy("ProductId").
                count().
                orderBy(new Column("count").desc()).
                limit(MOST_POPULAR_PRODUCTS_LIMIT);

        return SparkUtils.collectColumnToList(mostPopularProducts, "ProductId");
    }

    private List<String> getMostActiveUsers(Dataset<Row> dataSet) {

        Dataset<Row> mostActiveUsers = dataSet.
                groupBy("ProfileName").
                count().
                orderBy(new Column("count").desc()).
                limit(MOST_ACTIVE_USERS_LIMIT);

        return SparkUtils.collectColumnToList(mostActiveUsers, "ProfileName");
    }

    private List<String> getMostUsedWords(Dataset<Row> dataSet) {

        FlatMapFunction<Row, String> collectWordsFunction = row -> {

            List<String> list = new LinkedList<>();

            //TODO: more sophisticated splitting? Remove punctuation marks, and connection words?

            list.addAll(Arrays.asList(row.<String>getAs("Summary").split(" ")));
            list.addAll(Arrays.asList(row.<String>getAs("Text").split(" ")));

            return list.iterator();
        };

        Dataset<String> words = dataSet.flatMap(collectWordsFunction, Encoders.STRING());

        Dataset<Row> mostUsedWords = words.
                groupBy("value").
                count().
                orderBy(new Column("count").desc()).
                limit(MOST_USED_WORDS_LIMIT);

        return SparkUtils.collectColumnToList(mostUsedWords, "value");
    }
}
