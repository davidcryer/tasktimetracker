package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.IllegalArgsException;

import org.junit.Test;

import java.util.Date;

public class SessionTests {

    public static class InitTests {

        @Test
        public void valid() {
            new Session(new Date(0L), new Date(1L));
        }

        @Test(expected = IllegalArgsException.class)
        public void startNull_finishNull() {
            new Session(null, null);
        }

        @Test(expected = IllegalArgsException.class)
        public void startNonNull_AndAfter_NonNullFinish() {
            new Session(new Date(1L), new Date(0L));
        }
    }
}
