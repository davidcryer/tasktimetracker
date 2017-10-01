package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.IllegalArgsException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class TaskTests {
    private Task task;

    @Before
    public void setup() {
        task = new Task("", null);
    }

    @Test
    public void start() {
        task.start();
        Assert.assertTrue(task.isOngoing());
    }

    @Test(expected = AlreadyStartedException.class)
    public void start_alreadyStarted() {
        task.start();
        task.start();
    }

    @Test
    public void stop() {
        task.start();
        task.stop();
        Assert.assertFalse(task.isOngoing());
    }

    @Test(expected = AlreadyStoppedException.class)
    public void stop_alreadyStopped() {
        task.stop();
    }

    @Test
    public void expendedTime_beforeStart() {
        Assert.assertTrue(task.expendedTime() == 0L);
    }

    @Test
    public void expendedTime_afterStart_beforeStop_withDuration() throws Exception {
        task.start();
        Thread.sleep(10L);
        Assert.assertTrue(task.expendedTime() >= 10L && task.expendedTime() < 15L);
    }

    @Test
    public void expendedTime_afterStart_afterStop_withDuration() throws Exception {
        task.start();
        Thread.sleep(10L);
        task.stop();
        Thread.sleep(10L);
        Assert.assertTrue(task.expendedTime() >= 10L && task.expendedTime() < 15L);
    }

    @Test
    public void multipleStartAndStops_withDuration() throws Exception {
        task.start();
        Thread.sleep(10L);
        task.stop();
        task.start();
        Thread.sleep(10L);
        task.stop();
        Assert.assertTrue(task.expendedTime() >= 20L && task.expendedTime() < 30L);
    }

    @Test
    public void deleteSession() {
        final Task task = new Task(UUID.randomUUID(), "", null, null);
        task.start();
        task.stop();
        Assert.assertTrue(task.sessionHistory().size() == 1);
        Assert.assertTrue(task.deleteSession(task.sessionHistory().get(0).id()));
    }

    @Test
    public void deleteSession_nonMatchingId() {
        final Task task = new Task("", null);
        Assert.assertFalse(task.deleteSession(null));
    }

    public static class InitTests {

        @Test
        public void nonNullId() {
            new Task(UUID.randomUUID(), "", null, null);
        }

        @Test(expected = IllegalArgsException.class)
        public void nullId() {
            new Task(null, "", null, null);
        }

        @Test
        public void nullOngoingSession() {
            new Task(UUID.randomUUID(), "", null, null);
        }

        @Test(expected = IllegalArgsException.class)
        public void title_null() {
            new Task(UUID.randomUUID(), null, null, null);
        }

        @Test
        public void ongoingSession_isOngoing() {
            final OngoingSession session = new OngoingSession();
            new Task(UUID.randomUUID(), "", null, session);
        }

        @Test(expected = IllegalArgsException.class)
        public void ongoingSession_finished() {
            final OngoingSession session = new OngoingSession();
            session.stop();
            new Task(UUID.randomUUID(), "", null, session);
        }
    }

    public static class WriterTests {

        @Test
        public void changeTitle() {
            final Task task = new Task("Old title", null);
            task.writer().title("New title").commit();
            Assert.assertEquals(task.title(), "New title");
        }

        @Test(expected = IllegalArgsException.class)
        public void changeTitle_illegalTitle() {
            final Task task = new Task("Old title", null);
            task.writer().title(null).commit();
        }

        @Test
        public void changeNote() {
            final Task task = new Task("", "Old note");
            task.writer().note("New note").commit();
            Assert.assertEquals(task.note(), "New note");
        }

        @Test
        public void changeTitleAndNote() {
            final Task task = new Task("Old title", "Old note");
            task.writer().title("New title").note("New note").commit();
            Assert.assertEquals(task.title(), "New title");
            Assert.assertEquals(task.note(), "New note");
        }
    }
}
