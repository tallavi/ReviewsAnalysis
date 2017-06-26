package com.tallavi.roundforest1.reviews.analysis;

import java.util.List;

/**
 * Created by tallavi on 24/06/2017.
 */
public class ReviewsAnalysisResult {

    private List<String> mostPopularProducts;
    private List<String> mostActiveUsers;
    private List<String> mostUsedWords;

    public List<String> getMostPopularProducts() {
        return mostPopularProducts;
    }

    public void setMostPopularProducts(List<String> mostPopularProducts) {
        this.mostPopularProducts = mostPopularProducts;
    }

    public List<String> getMostActiveUsers() {
        return mostActiveUsers;
    }

    public void setMostActiveUsers(List<String> mostActiveUsers) {
        this.mostActiveUsers = mostActiveUsers;
    }

    public List<String> getMostUsedWords() {
        return mostUsedWords;
    }

    public void setMostUsedWords(List<String> mostUsedWords) {
        this.mostUsedWords = mostUsedWords;
    }
}
