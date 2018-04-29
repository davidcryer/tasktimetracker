package com.davidcryer.tasktimetracker.common.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class DbMapper {

    static List<DbTask> dbTasks(final Task[] tasks) {
        if (tasks == null) {
            return null;
        }
        return Arrays.stream(tasks).map(Task::toDbTask).collect(Collectors.toList());
    }

    static List<ObservedTask> tasks(final List<DbTask> dbTasks, final TaskFactory factory) {
        if (dbTasks == null) {
            return null;
        }
        return dbTasks.stream().map(dbTask -> dbTask.toTask(factory)).collect(Collectors.toList());
    }

    static List<DbFinishedSession> dbFinishedSessions(final List<FinishedSession> finishedSessions) {
        if (finishedSessions == null) {
            return null;
        }
        return finishedSessions.stream().map(FinishedSession::toDbFinishedSession).collect(Collectors.toList());
    }

    static List<FinishedSession> finishedSessions(final List<DbFinishedSession> dbFinishedSessions) {
        if (dbFinishedSessions == null) {
            return null;
        }
        return dbFinishedSessions.stream().map(DbFinishedSession::toFinishedSession).collect(Collectors.toList());
    }
}
