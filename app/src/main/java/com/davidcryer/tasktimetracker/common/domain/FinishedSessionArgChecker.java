package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.multiarg.ArgChecker;

import java.util.Date;
import java.util.UUID;

import static com.davidcryer.argrules.multiarg.ImmediateResult.forCheck;

class FinishedSessionArgChecker extends ArgChecker<FinishedSessionArgResults.Exception, FinishedSessionArgResults> {
    private final static String ERROR_ID_NULL = "id cannot be null";
    private final static String ERROR_START_NULL = "register cannot be null";
    private final static String ERROR_FINISH_NULL = "finish cannot be null";
    private final static String ERROR_TIMELINE_ILLEGAL = "register cannot be later than finish";
    private final FinishedSessionArgResults.Builder resultsBuilder = new FinishedSessionArgResults.Builder();

    FinishedSessionArgChecker id(final UUID id) {
        resultsBuilder.id(forCheck(id != null, ERROR_ID_NULL));
        return this;
    }

    FinishedSessionArgChecker times(final Date start, final Date finish) {
        resultsBuilder.start(forCheck(start != null, ERROR_START_NULL));//TODO can I streamline these and only do the last check if the first two pass?
        resultsBuilder.finish(forCheck(finish != null, ERROR_FINISH_NULL));
        resultsBuilder.timeline(forCheck(start == null || finish == null || start.compareTo(finish) <= 0, ERROR_TIMELINE_ILLEGAL));
        return this;
    }

    @Override
    public FinishedSessionArgResults results() {
        return resultsBuilder.create();
    }
}
