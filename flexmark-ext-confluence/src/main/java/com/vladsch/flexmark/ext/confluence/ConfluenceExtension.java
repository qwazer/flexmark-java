package com.vladsch.flexmark.ext.confluence;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.confluence.internal.ConfluenceBlockParser;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for ext_confluence
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 */
public class ConfluenceExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    private ConfluenceExtension() {
    }

    public static Extension create() {
        return new ConfluenceExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new ConfluenceBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        //todo
        //if (rendererType.equals("HTML")) {
        //    rendererBuilder.nodeRendererFactory(new AsideNodeRenderer.Factory());
        //} else if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        //}
    }
}
