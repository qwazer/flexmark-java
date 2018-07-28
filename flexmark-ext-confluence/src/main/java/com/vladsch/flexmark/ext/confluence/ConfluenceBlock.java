package com.vladsch.flexmark.ext.confluence;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * Markdown QuoteBlock with special header,
 * that represent Confluence Info, Tip, Note, and Warning Macros
 *
 * https://confluence.atlassian.com/doc/info-tip-note-and-warning-macros-51872369.html
 */
public class ConfluenceBlock extends Block {
    private BasedSequence openingMarker = BasedSequence.NULL;
    private BasedSequence type = BasedSequence.NULL;
    protected BasedSequence title = BasedSequence.NULL;

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                openingMarker,
                type,
                title,
        };
    }

    @Override
    public BasedSequence[] getSegmentsForChars() {
        return new BasedSequence[] {
                openingMarker,
                type,
                title,
        };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        BasedSequence content = getContentChars();
        int lines = getContentLines().size();
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, type, "type");
        segmentSpanChars(out, title, "title");
    }

    public ConfluenceBlock() {
    }

    public ConfluenceBlock(BasedSequence chars) {
        super(chars);
    }

    public ConfluenceBlock(BasedSequence chars, BasedSequence openingMarker, BasedSequence type, List<BasedSequence> segments) {
        super(chars, segments);
        this.openingMarker = openingMarker;
        this.type = type;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public void setType(BasedSequence type) {
        this.type = type;
    }

    public BasedSequence getType() {
        return type;
    }

    public BasedSequence getTitle() {
        return title;
    }

    public void setTitle(final BasedSequence title) {
        this.title = title;
    }

    public void setTitleChars(BasedSequence titleChars) {
        if (titleChars != null && titleChars != BasedSequence.NULL) {
            title = titleChars;
        } else {
            title = BasedSequence.NULL;
        }
    }

}
