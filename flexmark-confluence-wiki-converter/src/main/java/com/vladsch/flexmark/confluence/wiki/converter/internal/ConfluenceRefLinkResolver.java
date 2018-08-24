package com.vladsch.flexmark.confluence.wiki.converter.internal;

import com.vladsch.flexmark.ast.LinkRef;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.confluence.wiki.converter.ConfluenceWikiConverterExtension;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

import java.util.Set;

public class ConfluenceRefLinkResolver implements LinkResolver {


    public ConfluenceRefLinkResolver(final LinkResolverContext context) {
        // can use context for custom settings
        // context.getDocument();
        // context.getHtmlOptions();
    }

    @Override
    public ResolvedLink resolveLink(final Node node, final LinkResolverContext context, final ResolvedLink link) {
        if (node instanceof LinkRef) {
            String prefix = ConfluenceWikiConverterExtension.CONFLUENCE_LINK_PAGE_TITLE_PREFIX.getFrom(context.getOptions());
            String url = prefix + link.getUrl();
            return link.withStatus(LinkStatus.VALID)
                    .withUrl(url);
        }

        return link;
    }

    public static class Factory implements LinkResolverFactory {
        @Override
        public Set<Class<? extends LinkResolverFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends LinkResolverFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public LinkResolver create(final LinkResolverContext context) {
            return new ConfluenceRefLinkResolver(context);
        }
    }
}
