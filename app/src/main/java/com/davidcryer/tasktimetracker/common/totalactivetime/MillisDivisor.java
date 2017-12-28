package com.davidcryer.tasktimetracker.common.totalactivetime;

import static com.davidcryer.tasktimetracker.common.TimeInterval.MILLIS_IN_DAY;
import static com.davidcryer.tasktimetracker.common.TimeInterval.MILLIS_IN_HOUR;
import static com.davidcryer.tasktimetracker.common.TimeInterval.MILLIS_IN_MINUTE;
import static com.davidcryer.tasktimetracker.common.TimeInterval.MILLIS_IN_SECOND;
import static com.davidcryer.tasktimetracker.common.TimeInterval.MILLIS_IN_YEAR;

public class MillisDivisor {
    private long millis;

    MillisDivisor(long millis) {
        this.millis = millis;
    }

    long years() {
        return denominations(MILLIS_IN_YEAR);
    }

    long days() {
        return denominations(MILLIS_IN_DAY);
    }

    long hours() {
        return denominations(MILLIS_IN_HOUR);
    }

    long minutes() {
        return denominations(MILLIS_IN_MINUTE);
    }

    long seconds() {
        return denominations(MILLIS_IN_SECOND);
    }

    private long denominations(final long denom) {
        final long denoms = millis / denom;
        if (denoms != 0L) {
            millis %= (denoms * denom);
        }
        return denoms;
    }
}
