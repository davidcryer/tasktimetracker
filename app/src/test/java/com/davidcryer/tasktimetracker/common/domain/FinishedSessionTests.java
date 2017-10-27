package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.IllegalFinishedSessionArgsException;

import org.junit.Test;

import java.util.Date;

public class FinishedSessionTests {

    public static class InitTests {

        @Test
        public void valid() {
            new FinishedSession(new Date(0L), new Date(1L));
        }

        @Test(expected = IllegalFinishedSessionArgsException.class)
        public void startNull_finishNull() {
            new FinishedSession(null, null);
        }

        @Test(expected = IllegalFinishedSessionArgsException.class)
        public void startNonNull_AndAfter_NonNullFinish() {
            new FinishedSession(new Date(1L), new Date(0L));
        }
    }
}
