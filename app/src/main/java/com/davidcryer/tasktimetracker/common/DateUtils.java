package com.davidcryer.tasktimetracker.common;

import java.util.Date;

public class DateUtils {

    public static long difference(final Date start, final Date finish) {
        return finish.getTime() - start.getTime();
    }
}
