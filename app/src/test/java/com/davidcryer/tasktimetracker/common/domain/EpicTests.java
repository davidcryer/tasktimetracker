package com.davidcryer.tasktimetracker.common.domain;

import junit.framework.Assert;

import org.junit.Test;

public class EpicTests {

    @Test
    public void changeTitle() {
        final Epic epic = new Epic("Old title", null);
        epic.writer().title("New title").commit();
        Assert.assertEquals(epic.title(), "New title");
    }

    @Test
    public void changeNote() {
        final Epic epic = new Epic("Old note", null);
        epic.writer().note("New note").commit();
        Assert.assertEquals(epic.note(), "New note");
    }
}
