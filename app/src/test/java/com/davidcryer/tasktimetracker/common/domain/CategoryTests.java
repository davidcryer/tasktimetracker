package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.IllegalCategoryArgsException;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class CategoryTests {

    @Test
    public void changeTitle() {
        final Category category = new Category("Old title", null);
        category.writer().title("New title").commit();
        Assert.assertEquals(category.title(), "New title");
    }

    @Test
    public void changeNote() {
        final Category category = new Category("", "Old note");
        category.writer().note("New note").commit();
        Assert.assertEquals(category.note(), "New note");
    }

    @Test
    public void tasks_givesNewList() {
        final Category category = new Category("", null);
        final List<Task> tasks = category.tasks();
        tasks.add(new Task("", null));
        Assert.assertTrue(category.tasks().isEmpty());
    }

    @Test
    public void addTask() {
        final Category category = new Category("", null);
        final Task task = new Task("", null);
        Assert.assertTrue(category.addTask(task));
        Assert.assertTrue(!category.tasks().isEmpty());
        Assert.assertTrue(category.tasks().get(0) == task);
    }

    @Test
    public void addTask_alreadyAdded() {
        final Category category = new Category("", null);
        final Task task = new Task("", null);
        Assert.assertTrue(category.addTask(task));
        Assert.assertFalse(category.addTask(task));
        Assert.assertTrue(category.tasks().size() == 1);
    }

    @Test
    public void deleteTask() {
        final Category category = new Category("", null);
        final Task task = new Task("", null);
        category.addTask(task);
        Assert.assertTrue(category.deleteTask(task.id()));
        Assert.assertTrue(category.tasks().isEmpty());
    }

    @Test
    public void deleteTask_noMatchingTask() {
        final Category category = new Category("", null);
        final Task task = new Task("", null);
        Assert.assertFalse(category.deleteTask(task.id()));
    }

    @Test
    public void expendedTime() throws Exception {
        final Category category = new Category("", null);
        final Task task1 = new Task("", null);
        final Task task2 = new Task("", null);
        category.addTask(task1);
        category.addTask(task2);
        task1.start();
        task2.start();
        Thread.sleep(10L);
        task1.stop();
        task2.stop();
        final Long expendedTime = category.expendedTime();
        Assert.assertTrue(expendedTime >= 20L && expendedTime < 30L);
    }

    public static class InitTests {

        @Test
        public void valid() {
            new Category(UUID.randomUUID(), "", null, null);
        }

        @Test(expected = IllegalCategoryArgsException.class)
        public void illegalTitle() {
            new Category(null, null);
        }

        @Test(expected = IllegalCategoryArgsException.class)
        public void illegalId() {
            new Category(null, "", null, null);
        }
    }

    public static class WriterTests {

        @Test
        public void changeTitle() {
            final Category category = new Category("Old title", null);
            category.writer().title("New title").commit();
            Assert.assertEquals(category.title(), "New title");
        }

        @Test(expected = IllegalCategoryArgsException.class)
        public void changeTitle_illegalTitle() {
            final Category category = new Category("Old title", null);
            category.writer().title(null).commit();
        }

        @Test
        public void changeNote() {
            final Category category = new Category("", "Old note");
            category.writer().note("New note").commit();
            Assert.assertEquals(category.note(), "New note");
        }

        @Test
        public void changeTitleAndNote() {
            final Category category = new Category("Old title", "Old note");
            category.writer().title("New title").note("New note").commit();
            Assert.assertEquals(category.title(), "New title");
            Assert.assertEquals(category.note(), "New note");
        }
    }
}
