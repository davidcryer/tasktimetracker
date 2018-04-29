package com.davidcryer.tasktimetracker.common.domain;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ActivatedTaskRegisterTests {
    private ActivatedTaskRegister runner;

    @Before
    public void setup() {
        runner = new ActivatedTaskRegister(null);
    }

    @Test
    public void start() {
        final Task task = new Task("", null);
        runner.activate(task);
        Assert.assertTrue(task.isActive());
    }

    @Test
    public void start_nullTask() {
        runner.activate(null);
    }

    @Test
    public void stop() {
        final Task task = new Task("", null);
        runner.activate(task);
        runner.stopOngoingTask();
        Assert.assertFalse(task.isActive());
    }

    @Test
    public void stop_nothingStarted() {
        runner.stopOngoingTask();
    }

    @Test
    public void start_taskAlreadyOngoing() {
        final Task firstTask = new Task("", null);
        final Task secondTask = new Task("", null);
        runner.activate(firstTask);
        runner.activate(secondTask);
        Assert.assertFalse(firstTask.isActive());
        Assert.assertTrue(secondTask.isActive());
    }

    @Test
    public void start_withNullTask_andAlreadyRunningTask() {
        final Task firstTask = new Task("", null);
        runner.activate(firstTask);
        runner.activate(null);
        Assert.assertTrue(firstTask.isActive());
    }

    @Test
    public void start_withSameTask() {
        final Task firstTask = new Task("", null);
        runner.activate(firstTask);
        runner.activate(firstTask);
    }
}
