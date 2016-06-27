package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.Heading;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;

import java.util.Collections;
import java.util.Set;

public class ZzzzzzBlockPreProcessor implements BlockPreProcessor {
    final private ZzzzzzOptions options;

    public ZzzzzzBlockPreProcessor(DataHolder options) {
        this.options = new ZzzzzzOptions(options);
    }

    @Override
    public Block preProcess(ParserState state, Block block) {
        return block;
    }

    public static class Factory implements BlockPreProcessorFactory {
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            return Collections.singleton(Heading.class);
        }

        @Override
        public Set<Class<? extends BlockPreProcessorFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends BlockPreProcessorFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return true;
        }

        @Override
        public BlockPreProcessor create(ParserState state) {
            return new ZzzzzzBlockPreProcessor(state.getProperties());
        }
    }
}
