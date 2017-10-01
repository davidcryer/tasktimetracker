package com.davidcryer.tasktimetracker.common.domain;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

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

    @Test
    public void tasks_givesNewList() {
        final Story story = new Story(null, null);
        final List<Task> tasks = story.tasks();
        tasks.add(new Task(null, null));
        Assert.assertTrue(story.tasks().isEmpty());
    }

    public static class WriterTests {

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

        @Test
        public void changeTitleAndNote() {
            final Story story = new Story("Old title", "Old note");
            story.writer().title("New title").note("New note").commit();
            Assert.assertEquals(story.title(), "New title");
            Assert.assertEquals(story.note(), "New note");
        }

        @Test
        public void addTask() {
            final Story story = new Story(null, null);
            final Task task = new Task(null, null);
            story.writer().add(task).commit();
            Assert.assertTrue(story.tasks().get(0) == task);
            Assert.assertTrue(story.tasks().size() == 1);
        }

        @Test
        public void addTask_alreadyAdded() {
            final Story story = new Story(null, null);
            final Task task = new Task(null, null);
            story.writer().add(task).add(task).commit();
            Assert.assertTrue(story.tasks().size() == 1);
        }

        @Test
        public void deleteTask_differentWriter() {
            final Story story = new Story(null, null);
            final Task task = new Task(null, null);
            story.writer().add(task).commit();
            story.writer().delete(task).commit();
            Assert.assertTrue(story.tasks().isEmpty());
        }

        @Test
        public void deleteTask_sameWriter() {
            final Story story = new Story(null, null);
            final Task task = new Task(null, null);
            story.writer().add(task).delete(task).commit();
            Assert.assertTrue(story.tasks().isEmpty());
        }

        @Test
        public void deleteTask_withId() {
            final Story story = new Story(null, null);
            final Task task = new Task(null, null);
            story.writer().add(task).delete(task.id()).commit();
            Assert.assertTrue(story.tasks().isEmpty());
        }
    }
}
