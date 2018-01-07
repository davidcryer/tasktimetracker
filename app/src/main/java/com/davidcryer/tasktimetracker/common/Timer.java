package com.davidcryer.tasktimetracker.common;

import android.os.Handler;

import java.util.TimerTask;

public class Timer {
    private java.util.Timer timer;

    public void schedule(final Runnable task, final long delay, final long period) {
        cancel();
        timer = new java.util.Timer(false);
        timer.schedule(timerTask(task), delay, period);
    }

    private static TimerTask timerTask(final Runnable task) {
        final Handler handler = new Handler();
        return new TimerTask() {
            @Override
            public void run() {
                handler.post(task);
            }
        };
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
