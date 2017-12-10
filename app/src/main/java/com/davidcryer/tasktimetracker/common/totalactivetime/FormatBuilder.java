package com.davidcryer.tasktimetracker.common.totalactivetime;

public interface FormatBuilder {
    FormatBuilder years(final MillisDivisor divisor);
    FormatBuilder days(final MillisDivisor divisor);
    FormatBuilder hours(final MillisDivisor divisor);
    FormatBuilder minutes(final MillisDivisor divisor);
    FormatBuilder seconds(final MillisDivisor divisor);
    String toString();
}
