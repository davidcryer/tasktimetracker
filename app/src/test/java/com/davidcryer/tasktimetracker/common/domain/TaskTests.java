package com.davidcryer.tasktimetracker.common.domain;

import junit.framework.Assert;

import org.junit.Test;

public class TaskTests {

    @Test
    public void start() {
        final Task task = new Task();
        task.start();
        Assert.assertTrue(task.isOngoing());
    }

    @Test(expected = AlreadyStartedException.class)
    public void start_alreadyStarted() {
        final Task task = new Task();
        task.start();
        task.start();
    }

    @Test
    public void stop() {
        final Task task = new Task();
        task.start();
        task.stop();
        Assert.assertFalse(task.isOngoing());
    }

    @Test(expected = AlreadyStoppedException.class)
    public void stop_alreadyStopped() {
        final Task task = new Task();
        task.stop();
    }

    @Test
    public void expendedTime_beforeStart() {
        final Task task = new Task();
        Assert.assertTrue(task.getExpendedTime() == 0L);
    }

    @Test
    public void expendedTime_afterStart_beforeStop_withDuration() throws Exception {
        final Task task = new Task();
        task.start();
        Thread.sleep(10L);
        Assert.assertTrue(task.getExpendedTime() >= 10L && task.getExpendedTime() < 15L);
    }

    @Test
    public void expendedTime_afterStart_afterStop_withDuration() throws Exception {
        final Task task = new Task();
        task.start();
        Thread.sleep(10L);
        task.stop();
        Thread.sleep(10L);
        Assert.assertTrue(task.getExpendedTime() >= 10L && task.getExpendedTime() < 15L);
    }

    @Test
    public void multipleStartAndStops_withDuration() throws Exception {
        final Task task = new Task();
        task.start();
        Thread.sleep(10L);
        task.stop();
        task.start();
        Thread.sleep(10L);
        task.stop();
        Assert.assertTrue(task.getExpendedTime() >= 20L && task.getExpendedTime() < 30L);
    }
}
