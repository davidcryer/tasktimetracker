package com.davidcryer.tasktimetracker.common.totalactivetime;

public class MillisDivisor {
    private final static long MILLIS_IN_SECOND = 1000;
    private final static long MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
    private final static long MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60;
    private final static long MILLIS_IN_DAY = MILLIS_IN_HOUR * 24;
    private final static long MILLIS_IN_YEAR = MILLIS_IN_DAY * 365;//FIXME not strictly correct
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
        millis %= (denoms * denom);
        return denoms;
    }
}
