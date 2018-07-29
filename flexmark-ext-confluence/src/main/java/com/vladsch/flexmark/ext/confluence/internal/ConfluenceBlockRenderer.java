package com.vladsch.flexmark.ext.confluence.internal;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.confluence.ConfluenceBlock;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.jira.converter.internal.JiraConverterNodeRenderer;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConfluenceBlockRenderer implements NodeRenderer {

    private int inBlockQuote = 0; //todo

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<NodeRenderingHandler<? extends Node>>(Arrays.asList(
                new NodeRenderingHandler<ConfluenceBlock>(ConfluenceBlock.class, new CustomNodeRenderer<ConfluenceBlock>() {
                    @Override
                    public void render(ConfluenceBlock node, NodeRendererContext context, HtmlWriter html) { ConfluenceBlockRenderer.this.render(node, context, html); }
                })
        ));
    }

    private void render(ConfluenceBlock node, NodeRendererContext context, HtmlWriter html) {
        String macroOpen = "{"+node.getType().toLowerCase();
        html.line().raw(macroOpen).raw("}").line();
        inBlockQuote++;
        context.renderChildren(node);
        inBlockQuote--;
        html.line().raw(macroOpen).raw("}").blankLine();
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new ConfluenceBlockRenderer();
        }
    }
}
