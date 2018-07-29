package com.vladsch.flexmark.ext.confluence.internal;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.ext.confluence.ConfluenceBlock;
import com.vladsch.flexmark.internal.*;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfluenceBlockParser extends AbstractBlockParser {

    final private static String CONFLUENCE_BLOCK_TYPES_ALLOWED_SPELLINGS = "[Ii]nfo|INFO|[Tt]ip|TIP|[Ww]arning|WARNING|[Nn]ote|NOTE";
    final private static String CONFLUENCE_BLOCK_START_FORMAT = "^>\\s\\*\\*(%s):\\*\\*(?:\\s+([a-zA-Z_:]*))?\\s*$";

    final ConfluenceBlock block;
    //private BlockContent content = new BlockContent();
    private final ConfluenceOptions options;
    private final int contentIndent;
    private boolean hadBlankLine;

    ConfluenceBlockParser(ConfluenceOptions options, int contentIndent) {
        this.options = options;
        this.contentIndent = contentIndent;
        this.block = new ConfluenceBlock();
    }

    private int getContentIndent() {
        return contentIndent;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(ParserState state, BlockParser blockParser, final Block block) {
        return true;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        final int nonSpaceIndex = state.getNextNonSpaceIndex();
        if (state.isBlank()) {
            hadBlankLine = true;
            return BlockContinue.atIndex(nonSpaceIndex);
        } else if (!hadBlankLine && options.allowLazyContinuation) {
            return BlockContinue.atIndex(nonSpaceIndex);
        } else if (state.getIndent() >= options.contentIndent) {
            int contentIndent = state.getColumn() + options.contentIndent;
            return BlockContinue.atColumn(contentIndent);
        } else {
            return BlockContinue.none();
        }
    }

    @Override
    public void closeBlock(ParserState state) {
        block.setCharsFromContent();
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<Class<? extends CustomBlockParserFactory>>(Arrays.asList(
                //    BlockQuoteParser.Factory.class,
                    HeadingParser.Factory.class,
                    FencedCodeBlockParser.Factory.class,
                    HtmlBlockParser.Factory.class,
                    ThematicBreakParser.Factory.class,
                    ListBlockParser.Factory.class,
                    IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    static boolean isMarker(
            final ParserState state,
            final int index,
            final boolean inParagraph,
            final boolean inParagraphListItem,
            final ConfluenceOptions options
    ) {
        final boolean allowLeadingSpace = options.allowLeadingSpace;
        final boolean interruptsParagraph = options.interruptsParagraph;
        final boolean interruptsItemParagraph = options.interruptsItemParagraph;
        final boolean withLeadSpacesInterruptsItemParagraph = options.withSpacesInterruptsItemParagraph;
        CharSequence line = state.getLine();
        if (!inParagraph || interruptsParagraph) {
            if ((allowLeadingSpace || state.getIndent() == 0) && (!inParagraphListItem || interruptsItemParagraph)) {
                if (inParagraphListItem && !withLeadSpacesInterruptsItemParagraph) {
                    return state.getIndent() == 0;
                } else {
                    return state.getIndent() < state.getParsing().CODE_BLOCK_INDENT;
                }
            }
        }
        return false;
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private final ConfluenceOptions options;

        BlockFactory(DataHolder options) {
            super(options);
            this.options = new ConfluenceOptions(options);
        }



        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getIndent() >= 4) {
                return BlockStart.none();
            }

            int nextNonSpace = state.getNextNonSpaceIndex();
            BlockParser matched = matchedBlockParser.getBlockParser();
            boolean inParagraph = matched.isParagraphParser();
            boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

            if (isMarker(state, nextNonSpace, inParagraph, inParagraphListItem, options)) {
                BasedSequence line = state.getLine();
                BasedSequence trySequence = line.subSequence(nextNonSpace, line.length());
                Parsing parsing = state.getParsing();
                Pattern startPattern = Pattern.compile(String.format(CONFLUENCE_BLOCK_START_FORMAT, CONFLUENCE_BLOCK_TYPES_ALLOWED_SPELLINGS));
                Matcher matcher = startPattern.matcher(trySequence);

                if (matcher.find()) {
                    // confluence block
                   // BasedSequence openingMarker = line.subSequence(nextNonSpace + matcher.start(1), nextNonSpace + matcher.end(1));
                    BasedSequence blockType = line.subSequence(nextNonSpace + matcher.start(1), nextNonSpace + matcher.end(1));
                    BasedSequence titleChars = matcher.group(2) == null ? BasedSequence.NULL : line.subSequence(nextNonSpace + matcher.start(2), nextNonSpace + matcher.end(2));

                    int contentOffset = options.contentIndent;

                    ConfluenceBlockParser confluenceBlockParser = new ConfluenceBlockParser(options, contentOffset);
                   // confluenceBlockParser.block.setOpeningMarker(openingMarker);
                    confluenceBlockParser.block.setType(blockType);
                    confluenceBlockParser.block.setTitleChars(titleChars);

                    return BlockStart.of(confluenceBlockParser)
                            .atIndex(line.length());
                } else {
                    return BlockStart.none();
                }
            } else {
                return BlockStart.none();
            }
        }
    }
}
