package com.vladsch.flexmark.confluence.wiki.converter;

import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboConfluenceWikiConverterSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/confluence_wiki_converter_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            //.set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Arrays.asList(
                    ConfluenceWikiConverterExtension.create()
                    , StrikethroughSubscriptExtension.create()
                    , TablesExtension.create()
                    , WikiLinkExtension.create()
                    , InsExtension.create()
                    )
            )
            .set(WikiLinkExtension.ALLOW_ANCHORS, true)
            .set(ConfluenceWikiConverterExtension.CONFLUENCE_LINK_PAGE_TITLE_PREFIX, "My prefix - ");

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    static {
        optionsMap.put("list-no-auto-loose", new MutableDataSet().set(Parser.LISTS_AUTO_LOOSE, false));
        optionsMap.put("links-first", new MutableDataSet().set(WikiLinkExtension.LINK_FIRST_SYNTAX, true));
        optionsMap.put("gfm", new MutableDataSet()
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
        );

        optionsMap.put("keep-whitespace", new MutableDataSet().set(TablesExtension.TRIM_CELL_WHITESPACE, false));
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboConfluenceWikiConverterSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<Object[]>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    public DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public Parser parser() {
        return PARSER;
    }

    @Override
    public HtmlRenderer renderer() {
        return RENDERER;
    }
}
