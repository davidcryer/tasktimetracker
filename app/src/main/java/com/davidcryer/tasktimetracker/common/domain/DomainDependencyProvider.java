package com.davidcryer.tasktimetracker.common.domain;

public class DomainDependencyProvider {

    public static CategoryFactory categoryFactory() {
        return new CategoryFactory(taskFactory());
    }

    private static TaskFactory taskFactory() {
        return new TaskFactory(ongoingTaskRegister());
    }

    private static OngoingTaskRegister ongoingTaskRegister() {
        return new OngoingTaskRegister();
    }
}
