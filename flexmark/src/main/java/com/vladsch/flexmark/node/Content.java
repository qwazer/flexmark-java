package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

import java.util.List;

/**
 * A node that uses delimiters in the source form (e.g. <code>*bold*</code>).
 */
public interface Content {
    BasedSequence getSpanningChars();
    int getLineCount();
    BasedSequence getLineChars(int index);
    BasedSequence getContentChars();
    BasedSequence getContentChars(int startLine, int endLine);
    List<BasedSequence> getContentLines();
    List<BasedSequence> getContentLines(int startLine, int endLine);
}
