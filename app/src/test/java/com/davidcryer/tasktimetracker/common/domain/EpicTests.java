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

    //TODO addTask story


    public static class WriterTests {

        @Test
        public void changeTitle() {
            final Epic epic = new Epic("Old title", null);
            epic.writer().title("New title").commit();
            Assert.assertEquals(epic.title(), "New title");
        }

        @Test
        public void changeNote() {
            final Epic epic = new Epic(null, "Old note");
            epic.writer().note("New note").commit();
            Assert.assertEquals(epic.note(), "New note");
        }

        @Test
        public void changeTitleAndNote() {
            final Epic epic = new Epic("Old title", "Old note");
            epic.writer().title("New title").note("New note").commit();
            Assert.assertEquals(epic.title(), "New title");
            Assert.assertEquals(epic.note(), "New note");
        }
    }
}
