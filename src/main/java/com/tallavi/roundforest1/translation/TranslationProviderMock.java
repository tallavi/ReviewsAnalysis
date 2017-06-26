package com.tallavi.roundforest1.translation;

import java.util.Random;

/**
 * Created by tallavi on 26/06/2017.
 */
public class TranslationProviderMock implements ITranslationProvider {

    private Random random = new Random();

    @Override
    public Response send(Request request) throws InterruptedException {

        Thread.sleep(100 + random.nextInt(200));

        Response response = new Response();

        response.setText(request.getText());

        return response;
    }
}
