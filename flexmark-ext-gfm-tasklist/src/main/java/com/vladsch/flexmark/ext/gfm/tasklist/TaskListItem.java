package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * A Task list item
 */
public class TaskListItem extends ListItem {
    protected boolean isOrderedItem = false;

    @Override
    public void getAstExtra(StringBuilder out) {
        super.getAstExtra(out);
        if (isOrderedItem) out.append(" isOrderedItem");
        out.append(isItemDoneMarker() ? " isDone" : " isNotDone");
    }

    @Override
    public boolean isParagraphWrappingDisabled(Paragraph node, ListOptions listOptions, DataHolder options) {
        assert node.getParent() == this;

        // see if this is the first paragraph child item we handle our own paragraph wrapping for that one
        Node child = getFirstChild();
        while (child != null && !(child instanceof Paragraph)) child = child.getNext();
        return child == node;
    }

    public TaskListItem() {
    }

    public TaskListItem(BasedSequence chars) {
        super(chars);
    }

    public TaskListItem(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public TaskListItem(BlockContent blockContent) {
        super(blockContent);
    }

    public TaskListItem(ListItem block) {
        super(block);
        isOrderedItem = block instanceof OrderedListItem;
    }

    @Override
    public void setOpeningMarker(BasedSequence openingMarker) {
        throw new IllegalStateException();
    }

    public boolean isItemDoneMarker() {
        return !markerSuffix.matches("[ ]");
    }

    public boolean isOrderedItem() {
        return isOrderedItem;
    }

    public void setOrderedItem(boolean orderedItem) {
        isOrderedItem = orderedItem;
    }
}
