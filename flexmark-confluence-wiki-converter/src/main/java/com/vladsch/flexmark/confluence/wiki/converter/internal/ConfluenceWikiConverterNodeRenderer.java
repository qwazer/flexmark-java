package com.vladsch.flexmark.confluence.wiki.converter.internal;

import com.vladsch.flexmark.ast.LinkRef;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.jira.converter.internal.JiraConverterNodeRenderer;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class ConfluenceWikiConverterNodeRenderer extends JiraConverterNodeRenderer {

    private final ReferenceRepository referenceRepository;
    private final ListOptions listOptions;

    public ConfluenceWikiConverterNodeRenderer(DataHolder options) {
        super(options);
        this.referenceRepository = options.get(Parser.REFERENCES);
        this.listOptions = ListOptions.getFrom(options);
    }
    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {

        Set<NodeRenderingHandler<?>> parentHandlers = super.getNodeRenderingHandlers();
        NodeRenderingHandler<LinkRef> linkRefHandler = new NodeRenderingHandler<LinkRef>(LinkRef.class, new CustomNodeRenderer<LinkRef>() {
            @Override
            public void render(LinkRef node, NodeRendererContext context, HtmlWriter html) { ConfluenceWikiConverterNodeRenderer.this.render(node, context, html); }
        });

        return replaceNodeRenderingHandlerOfType(parentHandlers, LinkRef.class, linkRefHandler );
    }

    private static Set<NodeRenderingHandler<?>> replaceNodeRenderingHandlerOfType(
            final Set<NodeRenderingHandler<?>> handlers,
            final Class<LinkRef> aClass,
            final NodeRenderingHandler<LinkRef> handler) {

        Set<NodeRenderingHandler<?>> result = new HashSet<>();
        for (NodeRenderingHandler<?> item : handlers) {
            if (!item.getNodeType().equals(aClass)) {
                result.add(item);
            }
        }
        result.add(handler);
        return  result;
    }

    private void render(LinkRef node, NodeRendererContext context, HtmlWriter html) {
       html.raw("LinkRef"); //todo
    }


    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new ConfluenceWikiConverterNodeRenderer(options);
        }
    }
}
