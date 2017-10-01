package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.IllegalArgsException;

import junit.framework.Assert;

import org.junit.Test;

import java.util.LinkedList;
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
        final Story story = new Story("", "Old note");
        story.writer().note("New note").commit();
        Assert.assertEquals(story.note(), "New note");
    }

    @Test
    public void tasks_givesNewList() {
        final Story story = new Story("", null);
        final List<Task> tasks = story.tasks();
        tasks.add(new Task("", null));
        Assert.assertTrue(story.tasks().isEmpty());
    }

    public static class InitTests {

        @Test
        public void valid() {
            new Story("", null, new LinkedList<Task>());
        }

        @Test(expected = IllegalArgsException.class)
        public void illegalTitle() {
            new Story(null, null);
        }

        @Test(expected = IllegalArgsException.class)
        public void illegalTasks() {
            new Story("", null, null);
        }
    }

    public static class WriterTests {

        @Test
        public void changeTitle() {
            final Story story = new Story("Old title", null);
            story.writer().title("New title").commit();
            Assert.assertEquals(story.title(), "New title");
        }

        @Test(expected = IllegalArgsException.class)
        public void changeTitle_illegalTitle() {
            final Story story = new Story("Old title", null);
            story.writer().title(null).commit();
        }

        @Test
        public void changeNote() {
            final Story story = new Story("", "Old note");
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
            final Story story = new Story("", null);
            final Task task = new Task("", null);
            story.writer().add(task).commit();
            Assert.assertTrue(story.tasks().get(0) == task);
            Assert.assertTrue(story.tasks().size() == 1);
        }

        @Test
        public void addTask_alreadyAdded() {
            final Story story = new Story("", null);
            final Task task = new Task("", null);
            story.writer().add(task).add(task).commit();
            Assert.assertTrue(story.tasks().size() == 1);
        }

        @Test
        public void deleteTask_differentWriter() {
            final Story story = new Story("", null);
            final Task task = new Task("", null);
            story.writer().add(task).commit();
            story.writer().delete(task).commit();
            Assert.assertTrue(story.tasks().isEmpty());
        }

        @Test
        public void deleteTask_sameWriter() {
            final Story story = new Story("", null);
            final Task task = new Task("", null);
            story.writer().add(task).delete(task).commit();
            Assert.assertTrue(story.tasks().isEmpty());
        }

        @Test
        public void deleteTask_withId() {
            final Story story = new Story("", null);
            final Task task = new Task("", null);
            story.writer().add(task).delete(task.id()).commit();
            Assert.assertTrue(story.tasks().isEmpty());
        }
    }
}
