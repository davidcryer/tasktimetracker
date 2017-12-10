package com.davidcryer.tasktimetracker.common.totalactivetime;

import com.davidcryer.tasktimetracker.common.StringUtils;

public class HoursFormatBuilder implements FormatBuilder {
    private final static String FORMAT = "%1$s:%2$s:%3$s";
    private long hours;
    private long minutes;
    private long seconds;

    @Override
    public FormatBuilder years(MillisDivisor divisor) {
        return this;
    }

    @Override
    public FormatBuilder days(MillisDivisor divisor) {
        return this;
    }

    @Override
    public FormatBuilder hours(MillisDivisor divisor) {
        this.hours = divisor.hours();
        return this;
    }

    @Override
    public FormatBuilder minutes(MillisDivisor divisor) {
        this.minutes = divisor.minutes();
        return this;
    }

    @Override
    public FormatBuilder seconds(MillisDivisor divisor) {
        this.seconds = divisor.seconds();
        return this;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, formatted(hours), formatted(minutes), formatted(seconds));
    }

    private static String formatted(final long l) {
        return StringUtils.toMinTwoFigures(l);
    }
}
