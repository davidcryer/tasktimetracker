package com.davidcryer.tasktimetracker.common.domain;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TaskRunnerTests {
    private TaskRunner runner;

    @Before
    public void setup() {
        runner = new TaskRunner(null);
    }

    @Test
    public void start() {
        final Task task = new Task("", null);
        runner.start(task);
        Assert.assertTrue(task.isOngoing());
    }

    @Test
    public void start_nullTask() {
        runner.start(null);
    }

    @Test
    public void stop() {
        final Task task = new Task("", null);
        runner.start(task);
        runner.stop();
        Assert.assertFalse(task.isOngoing());
    }

    @Test
    public void stop_nothingStarted() {
        runner.stop();
    }

    @Test
    public void start_taskAlreadyOngoing() {
        final Task firstTask = new Task("", null);
        final Task secondTask = new Task("", null);
        runner.start(firstTask);
        runner.start(secondTask);
        Assert.assertFalse(firstTask.isOngoing());
        Assert.assertTrue(secondTask.isOngoing());
    }

    @Test
    public void start_withNullTask_andAlreadyRunningTask() {
        final Task firstTask = new Task("", null);
        runner.start(firstTask);
        runner.start(null);
        Assert.assertTrue(firstTask.isOngoing());
    }
}
