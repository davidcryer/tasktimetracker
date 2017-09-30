package com.davidcryer.tasktimetracker.common;

import java.util.Date;

public class Time {

    public static long difference(final Date start, final Date finish) {
        return finish.getTime() - start.getTime();
    }
}
