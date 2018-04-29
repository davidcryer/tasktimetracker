package com.davidcryer.tasktimetracker.common;

import android.os.Handler;

import java.util.TimerTask;

public class Timer {
    private java.util.Timer timer;
    private boolean isRunning = false;

    public void schedule(final Runnable task, final long delay, final long period) {
        cancel();
        isRunning = true;
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
        isRunning = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
