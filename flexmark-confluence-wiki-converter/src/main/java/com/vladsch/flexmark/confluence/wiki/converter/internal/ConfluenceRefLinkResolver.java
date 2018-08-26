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

public class ConfluenceRefLinkResolver implements LinkResolver {

    //copied from atlassian-renderer-8.0.5.jar:com.atlassian.renderer.util.UrlUtil
    public static final List<String> URL_PROTOCOLS = Collections.unmodifiableList(Arrays.asList("http://", "https://", "ftp://", "ftps://", "mailto:", "nntp://", "news://", "irc://", "file:"));


    public ConfluenceRefLinkResolver(final LinkResolverContext context) {
        // can use context for custom settings
        // context.getDocument();
        // context.getHtmlOptions();
    }

    @Override
    public ResolvedLink resolveLink(final Node node, final LinkResolverContext context, final ResolvedLink link) {
        if (node instanceof LinkRef || node instanceof Link) {

            ConfluenceWikiPageContext wikiPageContext =  ConfluenceWikiConverterExtension.CONFLUENCE_WIKI_PAGE_CONTEXT.getFrom(context.getOptions());

            //todo if not null, etc

            if (ConfluenceWikiPageContext.contains(wikiPageContext, normalizeUrl(link.getUrl()))){

               //todo String url = wikiPageContext,getPageTitleByURl
            }


            //String prefix = ConfluenceWikiConverterExtension.CONFLUENCE_LINK_PAGE_TITLE_PREFIX.getFrom(context.getOptions());
            //if (!startsWithInternetProtocolUrl(link.getUrl())) {
            //    //todo startsWithPageTitle - It is not [#anchor], not [spacekey:pagetitle], not [/2004/01/12/blogposttitle], not [~username]
            //    String url = prefix + link.getUrl();
            //    return link.withStatus(LinkStatus.VALID)
            //            .withUrl(url);
            //}

        }

        return link;
    }

    private String normalizeUrl(final String url) {
        return null;
    }

    public static boolean startsWithInternetProtocolUrl(String str) {
        if (!stringSet(str)) {
            return false;
        } else {
            String startOfLowerString = str.substring(0, Math.min(str.length(), 8)).toLowerCase();
            //todo simplify
            Iterator i$ = URL_PROTOCOLS.iterator();

            String protocol;
            do {
                if (!i$.hasNext()) {
                    return false;
                }

                protocol = (String)i$.next();
            } while(!startOfLowerString.startsWith(protocol));

            return true;
        }
    }

    public static final boolean stringSet(String string) {
        return string != null && !"".equals(string);
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
