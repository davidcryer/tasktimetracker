package com.davidcryer.tasktimetracker.common;

public interface TimeInterval {
    long MILLIS_IN_SECOND = 1000;
    long MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
    long MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60;
    long MILLIS_IN_DAY = MILLIS_IN_HOUR * 24;
    long MILLIS_IN_YEAR = MILLIS_IN_DAY * 365;//FIXME not strictly correct
}
