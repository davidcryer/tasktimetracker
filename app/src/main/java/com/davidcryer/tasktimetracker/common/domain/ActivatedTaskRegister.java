package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.domain.exceptions.AlreadyActiveException;
import com.davidcryer.tasktimetracker.common.domain.exceptions.AlreadyInactiveException;

class ActivatedTaskRegister {
    private Task activatedTask;

    void setUp(final Task task) {
        if (task != null) {
            safeDeactivateActivatedTask();
            activatedTask = task;
        }
    }

    void activate(final Task task) throws AlreadyActiveException {
        if (task != null) {
            task.onActivate();
            safeDeactivateActivatedTask();
            activatedTask = task;
        }
    }

    private void safeDeactivateActivatedTask() {
        try {
            deactivateActivatedTask();
        } catch (AlreadyInactiveException ignored) {}
    }

    private void deactivateActivatedTask() throws AlreadyInactiveException {
        if (activatedTask != null) {
            deactivate(activatedTask);
        }
    }

    void deactivate(final Task task) throws AlreadyInactiveException {
        if (activatedTask == task) {
            activatedTask.onDeactivate();
            activatedTask = null;
        }
    }

    interface Task {
        void onActivate() throws AlreadyActiveException;
        void onDeactivate() throws AlreadyInactiveException;
    }
}
