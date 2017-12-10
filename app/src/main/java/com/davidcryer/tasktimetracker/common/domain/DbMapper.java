package com.davidcryer.tasktimetracker.common.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class DbMapper {

    static List<DbTask> dbTasks(final List<Task> tasks) {
        final List<DbTask> dbTasks = new ArrayList<>(tasks.size());
        tasks.forEach(task -> dbTasks.add(task.toDbTask()));
        return dbTasks;
    }

    static List<Task> tasks(final List<DbTask> dbTasks, final TaskFactory factory, final Task.OngoingStatusListener ongoingStatusListener) {
        final List<Task> tasks = new LinkedList<>();
        dbTasks.forEach(dbTask -> tasks.add(dbTask.toTask(factory, ongoingStatusListener)));
        return tasks;
    }

    static List<DbFinishedSession> dbFinishedSessions(final List<FinishedSession> finishedSessions) {
        final List<DbFinishedSession> dbFinishedSessions = new ArrayList<>(finishedSessions.size());
        finishedSessions.forEach(finishedSession -> dbFinishedSessions.add(finishedSession.toDbFinishedSession()));
        return dbFinishedSessions;
    }

    static List<FinishedSession> finishedSessions(final List<DbFinishedSession> dbFinishedSessions) {
        final List<FinishedSession> finishedSessions = new LinkedList<>();
        dbFinishedSessions.forEach(dbFinishedSession -> finishedSessions.add(dbFinishedSession.toFinishedSession()));
        return finishedSessions;
    }
}
