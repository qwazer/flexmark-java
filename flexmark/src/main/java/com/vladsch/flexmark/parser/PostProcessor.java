package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.node.Node;

public interface PostProcessor {
    /**
     * @param Node the node to post-process
     * @return the result of post-processing, may be a modified {@code node} argument
     */
    Node process(Node Node);
}
