package com.vladsch.flexmark.confluence.wiki.converter;

import java.util.Map;

public class ConfluenceWikiPageContext {

    private String url;

    private Map<String,String> urlByTitles;

    public ConfluenceWikiPageContext() { //todo make privat add factory
    }

    public ConfluenceWikiPageContext(final String url, final Map<String, String> urlByTitles) {
        this.url = url;
        this.urlByTitles = urlByTitles;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public Map<String, String> getUrlByTitles() {
        return urlByTitles;
    }

    public void setUrlByTitles(final Map<String, String> urlByTitles) {
        this.urlByTitles = urlByTitles;
    }

    public static boolean contains(ConfluenceWikiPageContext context, final String url) {
        return false;
    }
}
