package com.davidcryer.tasktimetracker.common.domain;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class OngoingTaskRegisterTests {
    private OngoingTaskRegister runner;

    @Before
    public void setup() {
        runner = new OngoingTaskRegister(null);
    }

    @Test
    public void start() {
        final Task task = new Task("", null);
        runner.register(task);
        Assert.assertTrue(task.isOngoing());
    }

    @Test
    public void start_nullTask() {
        runner.register(null);
    }

    @Test
    public void stop() {
        final Task task = new Task("", null);
        runner.register(task);
        runner.stopOngoingTask();
        Assert.assertFalse(task.isOngoing());
    }

    @Test
    public void stop_nothingStarted() {
        runner.stopOngoingTask();
    }

    @Test
    public void start_taskAlreadyOngoing() {
        final Task firstTask = new Task("", null);
        final Task secondTask = new Task("", null);
        runner.register(firstTask);
        runner.register(secondTask);
        Assert.assertFalse(firstTask.isOngoing());
        Assert.assertTrue(secondTask.isOngoing());
    }

    @Test
    public void start_withNullTask_andAlreadyRunningTask() {
        final Task firstTask = new Task("", null);
        runner.register(firstTask);
        runner.register(null);
        Assert.assertTrue(firstTask.isOngoing());
    }

    @Test
    public void start_withSameTask() {
        final Task firstTask = new Task("", null);
        runner.register(firstTask);
        runner.register(firstTask);
    }
}
