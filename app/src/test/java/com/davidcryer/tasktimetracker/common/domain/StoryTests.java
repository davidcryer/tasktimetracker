package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.IllegalStoryArgsException;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;
import java.util.UUID;

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

    @Test
    public void addTask() {
        final Story story = new Story("", null);
        final Task task = new Task("", null);
        Assert.assertTrue(story.addTask(task));
        Assert.assertTrue(!story.tasks().isEmpty());
        Assert.assertTrue(story.tasks().get(0) == task);
    }

    @Test
    public void addTask_alreadyAdded() {
        final Story story = new Story("", null);
        final Task task = new Task("", null);
        Assert.assertTrue(story.addTask(task));
        Assert.assertFalse(story.addTask(task));
        Assert.assertTrue(story.tasks().size() == 1);
    }

    @Test
    public void deleteTask() {
        final Story story = new Story("", null);
        final Task task = new Task("", null);
        story.addTask(task);
        Assert.assertTrue(story.deleteTask(task.id()));
        Assert.assertTrue(story.tasks().isEmpty());
    }

    @Test
    public void deleteTask_noMatchingTask() {
        final Story story = new Story("", null);
        final Task task = new Task("", null);
        Assert.assertFalse(story.deleteTask(task.id()));
    }

    @Test
    public void expendedTime() throws Exception {
        final Story story = new Story("", null);
        final Task task1 = new Task("", null);
        final Task task2 = new Task("", null);
        story.addTask(task1);
        story.addTask(task2);
        task1.start();
        task2.start();
        Thread.sleep(10L);
        task1.stop();
        task2.stop();
        final Long expendedTime = story.expendedTime();
        Assert.assertTrue(expendedTime >= 20L && expendedTime < 30L);
    }

    public static class InitTests {

        @Test
        public void valid() {
            new Story(UUID.randomUUID(), "", null, null);
        }

        @Test(expected = IllegalStoryArgsException.class)
        public void illegalTitle() {
            new Story(null, null);
        }

        @Test(expected = IllegalStoryArgsException.class)
        public void illegalId() {
            new Story(null, "", null, null);
        }
    }

    public static class WriterTests {

        @Test
        public void changeTitle() {
            final Story story = new Story("Old title", null);
            story.writer().title("New title").commit();
            Assert.assertEquals(story.title(), "New title");
        }

        @Test(expected = IllegalStoryArgsException.class)
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
    }
}
