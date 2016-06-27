package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.internal.util.collection.DataHolder;

public class WikiLinkOptions {
    final public boolean linkFirstSyntax;
    final public String linkFileExtension;

    public WikiLinkOptions(DataHolder options) {
        this.linkFirstSyntax = options.get(WikiLinkExtension.LINK_FIRST_SYNTAX);
        this.linkFileExtension = options.get(WikiLinkExtension.LINK_FILE_EXTENSION);
    }
}
