package com.tallavi.roundforest1.reviews.translation;

import com.tallavi.roundforest1.translation.TranslationService;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import com.tallavi.roundforest1.utils.SparkUtils;

import java.util.Random;

/**
 * Created by tallavi on 26/06/2017.
 */
public class ReviewsTranslationService {

    private static Logger logger = Logger.getLogger(ReviewsTranslationService.class);

    private SparkSession sparkSession;

    private TranslationService translationService;

    public ReviewsTranslationService(SparkSession sparkSession, TranslationService translationService) {
        this.sparkSession = sparkSession;
        this.translationService = translationService;
    }

    public void translate(String path) {

        Dataset<Row> dataSet = SparkUtils.loadDataSet(sparkSession, path);

        Dataset<Row> repartitioned = dataSet.repartition(100);

        Random random = new Random();

        repartitioned.foreach(row -> {

            String text = row.getString(row.fieldIndex("Text"));

            logger.info(String.format("Thread id = %d. Translating: %s", Thread.currentThread().getId(), text));

//            String translated = translationService.translate(text); //TODO: translation service cannot be serialized so it can't be used inside the task yet

            Thread.sleep(100 + random.nextInt(200));

            String translated = text;

            logger.info(String.format("Translated: %s", translated));
        });
    }
}
