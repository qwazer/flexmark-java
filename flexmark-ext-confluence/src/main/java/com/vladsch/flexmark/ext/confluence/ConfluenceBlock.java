package com.vladsch.flexmark.ext.confluence;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Markdown QuoteBlock with special header,
 * that represent Confluence Info, Tip, Note, and Warning Macros
 *
 * https://confluence.atlassian.com/doc/info-tip-note-and-warning-macros-51872369.html
 */
public class ConfluenceBlock extends BlockQuote {

    private Type type;

    private String title;


    public enum Type{
        INFO,
        NOTE,
        TIP,
        WARNING
    }



}
