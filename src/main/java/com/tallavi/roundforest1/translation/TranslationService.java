package com.tallavi.roundforest1.translation;

import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * Created by tallavi on 26/06/2017.
 */
public class TranslationService {

    private static Logger logger = Logger.getLogger(TranslationService.class);

    private ITranslationProvider translationProvider;

    public TranslationService(ITranslationProvider translationProvider) {
        this.translationProvider = translationProvider;
    }

    public String translate(String text) throws InterruptedException {

        ITranslationProvider.Request request = new ITranslationProvider.Request();

        request.setInput_lang("en");
        request.setOutput_lang("fr");
        request.setText(text);

        ITranslationProvider.Response response = null;

        try {

            response = this.translationProvider.send(request);
        }
        catch (Throwable t) {

            logger.error("Error while translating", t);
        }

        if (response != null)
            return response.getText();
        else
            return null;
    }
}
