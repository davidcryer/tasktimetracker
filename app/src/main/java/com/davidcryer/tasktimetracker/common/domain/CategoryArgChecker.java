package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.multiarg.ArgChecker;
import com.davidcryer.argrules.multiarg.ResultChain;

import java.util.UUID;

import static com.davidcryer.argrules.multiarg.DelayedResult.delayed;
import static com.davidcryer.argrules.multiarg.ImmediateResult.forCheck;

class CategoryArgChecker extends ArgChecker<CategoryArgResults.Exception, CategoryArgResults> {
    private final static String ERROR_ID_NULL = "id cannot be null";
    private final static String ERROR_TITLE_NULL = "title cannot be null";
    private final static String ERROR_TITLE_EMPTY = "title cannot be empty";
    private final CategoryArgResults.Builder resultsBuilder = new CategoryArgResults.Builder();

    CategoryArgChecker id(final UUID id) {
        resultsBuilder.id(forCheck(id != null, ERROR_ID_NULL));
        return this;
    }

    CategoryArgChecker title(final String title) {
        resultsBuilder.title(ResultChain.chain()
                .add(delayed(() -> title != null, ERROR_TITLE_NULL))
                .add(delayed(() -> !title.isEmpty(), ERROR_TITLE_EMPTY))
                .firstFailing()
        );
        return this;
    }

    @Override
    protected CategoryArgResults results() {
        return resultsBuilder.results();
    }
}
