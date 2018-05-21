package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.multiarg.ArgChecker;
import com.davidcryer.tasktimetracker.common.domain.exceptions.TaskArgResults;

import java.util.UUID;

import static com.davidcryer.argrules.multiarg.DelayedResult.delayed;
import static com.davidcryer.argrules.multiarg.ImmediateResult.forCheck;
import static com.davidcryer.argrules.multiarg.ResultChain.chain;

class TaskArgChecker extends ArgChecker<TaskArgResults.Exception, TaskArgResults> {
    private final static String ERROR_ID_NULL = "id cannot be null";
    private final static String ERROR_TITLE_NULL = "title cannot be null";
    private final static String ERROR_TITLE_EMPTY = "title cannot be empty";
    private final static String ERROR_ONGOING_SESSION_FINISHED = "ongoing session cannot be finished";
    private final TaskArgResults.Builder resultsBuilder;

    TaskArgChecker() {
        resultsBuilder = new TaskArgResults.Builder();
    }

    TaskArgChecker id(final UUID id) {
        resultsBuilder.id(forCheck(id != null, ERROR_ID_NULL));
        return this;
    }

    TaskArgChecker title(final String title) {
        resultsBuilder.title(chain()
                .add(delayed(() -> title != null, ERROR_TITLE_NULL))
                .add(delayed(() -> !title.isEmpty(), ERROR_TITLE_EMPTY))
                .firstFailing()
        );
        return this;
    }

    TaskArgChecker ongoingSession(final OngoingSession ongoingSession) {
        resultsBuilder.ongoingSession(forCheck(ongoingSession == null || !ongoingSession.isFinished(), ERROR_ONGOING_SESSION_FINISHED));
        return this;
    }

    @Override
    public TaskArgResults results() {
        return resultsBuilder.create();
    }
}
