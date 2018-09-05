package com.vladsch.flexmark.confluence.wiki.converter;

import java.util.Map;

/**
 * <p>
 * ConfluenceWikiPageContext holds <code>urlReplacements</code> map.
 * </p>
 * For example, if you have file "first.md" with link to page "second.md"
 * <code>
 *     [second.md]
 * </code>
 *
 * And title of page "second.md" in Confluence is "My second title".
 * For replacement of [second.md] to
 * <code>
 *     [My second title]
 * </code>
 * put appropriate replacement entry to urlReplacements map
 * and pass ConfluenceWikiPageContext object as parameter for renderer.
 */
public class ConfluenceWikiPageContext {


    private Map<String,String> urlReplacements;


    public ConfluenceWikiPageContext(final Map<String, String> urlReplacements) {
        this.urlReplacements = urlReplacements;
    }

    public Map<String, String> getUrlReplacements() {
        return urlReplacements;
    }

    public void setUrlReplacements(final Map<String, String> urlReplacements) {
        this.urlReplacements = urlReplacements;
    }

    public static boolean contains(ConfluenceWikiPageContext context, final String url) {
        return false;
    }
}
