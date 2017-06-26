package com.tallavi.roundforest1.translation;

import java.io.Serializable;

/**
 * Created by tallavi on 26/06/2017.
 */
public interface ITranslationProvider {

    class Request {

        private String input_lang;

        private String output_lang;

        private String text;

        public String getInput_lang() {
            return input_lang;
        }

        public void setInput_lang(String input_lang) {
            this.input_lang = input_lang;
        }

        public String getOutput_lang() {
            return output_lang;
        }

        public void setOutput_lang(String output_lang) {
            this.output_lang = output_lang;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    class Response {

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    Response send(Request request) throws InterruptedException;
}
