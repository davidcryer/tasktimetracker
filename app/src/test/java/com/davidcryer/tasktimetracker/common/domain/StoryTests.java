package com.davidcryer.tasktimetracker.common.domain;

import junit.framework.Assert;

import org.junit.Test;

public class StoryTests {

    @Test
    public void changeTitle() {
        final Story story = new Story("Old title", null);
        story.writer().title("New title").commit();
        Assert.assertEquals(story.title(), "New title");
    }

    @Test
    public void changeNote() {
        final Story story = new Story(null, "Old note");
        story.writer().note("New note").commit();
        Assert.assertEquals(story.note(), "New note");
    }
}
