package com.vladsch.flexmark.ext.confluence.internal;

import com.vladsch.flexmark.ext.confluence.ConfluenceExtension;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Map;

public class ConfluenceOptions {
    public final int contentIndent;
    public final boolean allowLeadingSpace;
    public final boolean interruptsParagraph;
    public final boolean interruptsItemParagraph;
    public final boolean withSpacesInterruptsItemParagraph;
    public final boolean allowLazyContinuation;
    //public final String unresolvedQualifier;
    //public final Map<String,String> qualifierTypeMap;
    //public final Map<String,String> qualifierTitleMap;
    //public final Map<String,String> typeSvgMap;

    public ConfluenceOptions(DataHolder options) {
        //contentIndent = ConfluenceExtension.CONTENT_INDENT.getFrom(options);
        //allowLeadingSpace = ConfluenceExtension.ALLOW_LEADING_SPACE.getFrom(options);
        //interruptsParagraph = ConfluenceExtension.INTERRUPTS_PARAGRAPH.getFrom(options);
        //interruptsItemParagraph = ConfluenceExtension.INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
        //withSpacesInterruptsItemParagraph = ConfluenceExtension.WITH_SPACES_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
        //allowLazyContinuation = ConfluenceExtension.ALLOW_LAZY_CONTINUATION.getFrom(options);
        //unresolvedQualifier = ConfluenceExtension.UNRESOLVED_QUALIFIER.getFrom(options);
        //qualifierTypeMap = ConfluenceExtension.QUALIFIER_TYPE_MAP.getFrom(options);
        //qualifierTitleMap = ConfluenceExtension.QUALIFIER_TITLE_MAP.getFrom(options);
        //typeSvgMap = ConfluenceExtension.TYPE_SVG_MAP.getFrom(options);
       //todo
        contentIndent = 0;
        interruptsParagraph=false;
        interruptsItemParagraph=false;
        withSpacesInterruptsItemParagraph=false;
        allowLazyContinuation=false;
        allowLeadingSpace = false;
    }
}
