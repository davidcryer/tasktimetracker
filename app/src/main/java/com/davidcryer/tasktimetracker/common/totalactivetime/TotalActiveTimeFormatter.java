package com.davidcryer.tasktimetracker.common.totalactivetime;

public final class TotalActiveTimeFormatter {

    private TotalActiveTimeFormatter() {}

    public static String toString(final long totalActiveTime, final FormatBuilder builder) {
        final MillisDivisor millisDivisor = new MillisDivisor(totalActiveTime);
        return builder
                .years(millisDivisor)
                .days(millisDivisor)
                .hours(millisDivisor)
                .minutes(millisDivisor)
                .seconds(millisDivisor)
                .toString();
    }
}
