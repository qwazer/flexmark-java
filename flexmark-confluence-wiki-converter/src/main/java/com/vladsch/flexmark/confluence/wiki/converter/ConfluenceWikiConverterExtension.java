package com.vladsch.flexmark.confluence.wiki.converter;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.confluence.wiki.converter.internal.ConfluenceRefLinkResolver;
import com.vladsch.flexmark.confluence.wiki.converter.internal.ConfluenceWikiConverterNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

public class ConfluenceWikiConverterExtension implements  Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    public static final DataKey<String> CONFLUENCE_LINK_PAGE_TITLE_PREFIX = new DataKey<String>("CONFLUENCE_LINK_PAGE_TITLE_PREFIX", "");
    public static final DataKey<ConfluenceWikiPageContext> CONFLUENCE_WIKI_PAGE_CONTEXT = new DataKey<ConfluenceWikiPageContext>("CONFLUENCE_WIKI_PAGE_CONTEXT", new ConfluenceWikiPageContext(null));


    private ConfluenceWikiConverterExtension() {
    }

    public static Extension create() {
        return new ConfluenceWikiConverterExtension();
    }

    @Override
    public void extend(final Parser.Builder parserBuilder) {

    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {
        final String rendererType = HtmlRenderer.TYPE.getFrom(options);
        if (rendererType.equals("HTML")) {
            // add equivalence between CONFLUENCE_WIKI and JIRA
            HtmlRenderer.addRenderTypeEquivalence(options, "CONFLUENCE_WIKI", "JIRA");
            options.set(HtmlRenderer.TYPE, "CONFLUENCE_WIKI");
        } else if (!rendererType.equals("CONFLUENCE_WIKI")) {
            throw new IllegalStateException("Non HTML Renderer is already set to " + rendererType);
        }
    }


    @Override
    public void parserOptions(final MutableDataHolder options) {

    }


    @Override
    public void extend(final HtmlRenderer.Builder rendererBuilder, final String rendererType) {
        if (rendererBuilder.isRendererType("CONFLUENCE_WIKI")) {
            rendererBuilder.linkResolverFactory(new ConfluenceRefLinkResolver.Factory());
            rendererBuilder.nodeRendererFactory(new ConfluenceWikiConverterNodeRenderer.Factory());
        } else {
            throw new IllegalStateException("Confluence Wiki Converter Extension used with non Confluence Wiki Renderer " + rendererType);
        }
    }
}
