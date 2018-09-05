package com.vladsch.flexmark.confluence.wiki.converter.internal;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.LinkRef;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.confluence.wiki.converter.ConfluenceWikiConverterExtension;
import com.vladsch.flexmark.confluence.wiki.converter.ConfluenceWikiPageContext;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

import java.util.*;

/**
 * Resolve link to Confluence page title if ConfluenceWikiPageContext has information about replacement of url to Confluence page title.
 */
public class ConfluenceWikiLinkResolver implements LinkResolver {

    public ConfluenceWikiLinkResolver(final LinkResolverContext context) {
        // can use context for custom settings
        // context.getDocument();
        // context.getHtmlOptions();
    }

    @Override
    public ResolvedLink resolveLink(final Node node, final LinkResolverContext context, final ResolvedLink link) {
        if (node instanceof LinkRef || node instanceof Link) {

            ConfluenceWikiPageContext wikiPageContext =  ConfluenceWikiConverterExtension.CONFLUENCE_WIKI_PAGE_CONTEXT.getFrom(context.getOptions());
            Map<String,String> urlReplacements = wikiPageContext.getUrlReplacements();

            if (urlReplacements!=null && urlReplacements.containsKey(link.getUrl())){
                    return link.withStatus(LinkStatus.VALID)
                            .withUrl(urlReplacements.get(link.getUrl()));
            }
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
            return new ConfluenceWikiLinkResolver(context);
        }
    }
}
