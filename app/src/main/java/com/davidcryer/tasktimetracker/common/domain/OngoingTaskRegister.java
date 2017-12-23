package com.davidcryer.tasktimetracker.common.domain;

public class OngoingTaskRegister {
    private Task ongoingTask;

    void register(final Task task) throws AlreadyStartedException {
        if (task != null) {
            task.onRegister();
            safeStopOngoingTask();
            ongoingTask = task;
        }
    }

    private void safeStopOngoingTask() {
        try {
            stopOngoingTask();
        } catch (AlreadyStoppedException ignored) {}
    }

    private void stopOngoingTask() throws AlreadyStoppedException {
        if (ongoingTask != null) {
            unregister(ongoingTask);
        }
    }

    void unregister(final Task task) throws AlreadyStoppedException {
        if (ongoingTask == task) {
            ongoingTask.onUnregister();
            ongoingTask = null;
        }
    }

    interface Task {
        void onRegister() throws AlreadyStartedException;
        void onUnregister() throws AlreadyStoppedException;
    }
}
