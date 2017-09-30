package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.IllegalArgsException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class SessionTests {
    private Session session;

    @Before
    public void setup() {
        session = new Session();
    }

    @Test
    public void start() {
        session.start();
        Assert.assertTrue(session.isOngoing());
    }

    @Test(expected = AlreadyStartedException.class)
    public void start_alreadyStarted() {
        session.start();
        session.start();
    }

    @Test
    public void stop() {
        session.start();
        session.stop();
        Assert.assertFalse(session.isOngoing());
    }

    @Test(expected = AlreadyStoppedException.class)
    public void stop_alreadyStopped() {
        session.stop();
    }

    @Test
    public void expendedTime_afterStart_beforeStop_withDuration() throws Exception {
        session.start();
        Thread.sleep(10L);
        Assert.assertTrue(session.duration() >= 10L && session.duration() < 15L);
    }

    @Test
    public void expendedTime_afterStart_afterStop_withDuration() throws Exception {
        session.start();
        Thread.sleep(10L);
        session.stop();
        Thread.sleep(10L);
        Assert.assertTrue(session.duration() >= 10L && session.duration() < 15L);
    }

    public static class InitTests {

        @Test
        public void startNull_finishNull() {
            new Session(null, null);
        }

        @Test(expected = IllegalArgsException.class)
        public void startNull_finishNonNull() {
            new Session(null, new Date());
        }

        @Test
        public void startNonNull_AndBefore_NonNullFinish() {
            new Session(new Date(0L), new Date(1L));
        }

        @Test(expected = IllegalArgsException.class)
        public void startNonNull_AndAfter_NonNullFinish() {
            new Session(new Date(1L), new Date(0L));
        }
    }
}
