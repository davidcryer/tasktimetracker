package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.IllegalArgsException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TaskTests {
    private Task task;

    @Before
    public void setup() {
        task = new Task();
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
        Assert.assertTrue(task.getExpendedTime() == 0L);
    }

    @Test
    public void expendedTime_afterStart_beforeStop_withDuration() throws Exception {
        task.start();
        Thread.sleep(10L);
        Assert.assertTrue(task.getExpendedTime() >= 10L && task.getExpendedTime() < 15L);
    }

    @Test
    public void expendedTime_afterStart_afterStop_withDuration() throws Exception {
        task.start();
        Thread.sleep(10L);
        task.stop();
        Thread.sleep(10L);
        Assert.assertTrue(task.getExpendedTime() >= 10L && task.getExpendedTime() < 15L);
    }

    @Test
    public void multipleStartAndStops_withDuration() throws Exception {
        task.start();
        Thread.sleep(10L);
        task.stop();
        task.start();
        Thread.sleep(10L);
        task.stop();
        Assert.assertTrue(task.getExpendedTime() >= 20L && task.getExpendedTime() < 30L);
    }

    public static class InitTests {

        @Test
        public void nullOngoingSession() {
            new Task(null);
        }

        @Test
        public void ongoingSession_isOngoing() {
            final Session session = new Session();
            session.start();
            new Task(session);
        }

        @Test(expected = IllegalArgsException.class)
        public void ongoingSession_notStarted() {
            final Session session = new Session();
            new Task(session);
        }

        @Test(expected = IllegalArgsException.class)
        public void ongoingSession_finished() {
            final Session session = new Session();
            session.start();
            session.stop();
            new Task(session);
        }
    }
}
