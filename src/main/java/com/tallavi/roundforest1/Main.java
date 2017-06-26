package com.tallavi.roundforest1;

import com.tallavi.roundforest1.reviews.analysis.ReviewsAnalysisResult;
import com.tallavi.roundforest1.reviews.analysis.ReviewsAnalysisService;
import com.tallavi.roundforest1.translation.ITranslationProvider;
import com.tallavi.roundforest1.translation.TranslationProviderMock;
import com.tallavi.roundforest1.translation.TranslationService;
import org.apache.log4j.Logger;
import org.apache.spark.sql.*;
import com.tallavi.roundforest1.reviews.translation.ReviewsTranslationService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tallavi on 24/06/2017.
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        if (args.length == 0) {

            logger.fatal("First parameter must be the path to Reviews.csv");
            return;
        }

        boolean shouldTranslate = false;

        List<String> argsList = Arrays.asList(args);

        if (argsList.contains("translate=true"))
            shouldTranslate = true;

        String path = args[0];

        logger.info(String.format("Path = '%s'", path));
        logger.info(String.format("Should translate = %s", shouldTranslate));

        SparkSession sparkSession = SparkSession.builder().
                appName("RoundForest1").
                master("local[4]").
                config("spark.executor.memory", "512m").
                getOrCreate();

        ReviewsAnalysisService reviewsAnalysisService = new ReviewsAnalysisService(sparkSession);

        ReviewsAnalysisResult reviewsAnalysisResult = reviewsAnalysisService.analyze(path);

        logger.info(String.format("Most popular products: %s", reviewsAnalysisResult.getMostPopularProducts()));

        logger.info(String.format("Most active users: %s", reviewsAnalysisResult.getMostActiveUsers()));

        if (shouldTranslate) {

            ITranslationProvider translationProvider = new TranslationProviderMock();

            TranslationService translationService = new TranslationService(translationProvider);

            ReviewsTranslationService reviewsTranslationService = new ReviewsTranslationService(sparkSession, translationService);

            reviewsTranslationService.translate(path);
        }
    }
}
