package com.davidcryer.tasktimetracker.common.domain;

public class TaskRunner {
    private Task ongoingTask;

    public TaskRunner(Task ongoingTask) {
        this.ongoingTask = ongoingTask;
    }

    public void start(final Task task) {
        if (task == null) {
            return;
        }
        stop();
        ongoingTask = task;
        task.start();
    }

    public void stop() {
        if (ongoingTask != null) {
            ongoingTask.stop();
            ongoingTask = null;
        }
    }
}
