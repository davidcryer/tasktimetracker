package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.multiarg.ArgChecker;

import java.util.Date;

import static com.davidcryer.argrules.multiarg.ImmediateResult.forCheck;

class OngoingSessionArgChecker extends ArgChecker<OngoingSessionArgResults.Exception, OngoingSessionArgResults> {
    private final static String ERROR_START_NULL = "onActivate cannot be null";
    private final OngoingSessionArgResults.Builder resultsBuilder = new OngoingSessionArgResults.Builder();

    OngoingSessionArgChecker start(final Date start) {
        resultsBuilder.start(forCheck(start != null, ERROR_START_NULL));
        return this;
    }

    @Override
    public OngoingSessionArgResults results() {
        return resultsBuilder.create();
    }
}
