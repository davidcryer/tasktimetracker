package com.davidcryer.tasktimetracker.common.domain;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class OngoingSessionTests {
    private OngoingSession ongoingSession;

    @Before
    public void setup() {
        ongoingSession = new OngoingSession();
    }

    @Test
    public void stop() {
        ongoingSession.stop();
    }

    @Test(expected = AlreadyStoppedException.class)
    public void stop_alreadyStopped() {
        ongoingSession.stop();
        ongoingSession.stop();
    }

    @Test
    public void expendedTime_afterStart_beforeStop_withDuration() throws Exception {
        Thread.sleep(10L);
        Assert.assertTrue(ongoingSession.duration() >= 10L && ongoingSession.duration() < 15L);
    }
}
