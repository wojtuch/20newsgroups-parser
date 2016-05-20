package com.wojtuch.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wojlukas on 5/20/16.
 */
public class NewsgroupsArticle implements Serializable {
    private Map<String, String> headers = new HashMap<>();
    private String rawText;

    public NewsgroupsArticle() {
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
