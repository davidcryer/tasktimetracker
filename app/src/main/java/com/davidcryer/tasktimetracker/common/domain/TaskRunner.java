package com.davidcryer.tasktimetracker.common.domain;

public class TaskRunner {
    private Task ongoingTask;

    public TaskRunner(Task ongoingTask) {
        this.ongoingTask = ongoingTask;
    }

    public void start(final Task task) throws AlreadyStartedException {
        if (task == null || task == ongoingTask) {
            return;
        }
        stop();
        ongoingTask = task;
        task.start();
    }

    public void stop() throws AlreadyStoppedException {
        if (ongoingTask != null) {
            ongoingTask.stop();
            ongoingTask = null;
        }
    }
}
