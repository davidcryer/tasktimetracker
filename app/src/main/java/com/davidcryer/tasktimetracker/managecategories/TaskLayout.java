package com.davidcryer.tasktimetracker.managecategories;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.totalactivetime.HoursFormatBuilder;
import com.davidcryer.tasktimetracker.common.totalactivetime.TotalActiveTimeFormatter;

public class TaskLayout extends LinearLayout {
    private final static IntentFilter INTENT_FILTER_TICK = new IntentFilter(Intent.ACTION_TIME_TICK);
    private final TextView titleView;
    private final TextView timeView;
    private final Switch switchView;
    private UiTask task;
    private BroadcastReceiver broadcastReceiver;
    private Long timeOfLastTick;

    public TaskLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_task, this);
        titleView = findViewById(R.id.title);
        timeView = findViewById(R.id.time);
        switchView = findViewById(R.id.selector);
    }

    void task(final UiTask task) {
        this.task = task;
        title(task.getTitle());
        totalTimeActive(task.getTotalTimeActive());
        isActive(task.isActive());
        setUpClockIfActive(task);
    }

    private void title(final String title) {
        titleView.setText(title);
    }

    private void totalTimeActive(final long totalTimeActive) {
        timeView.setText(TotalActiveTimeFormatter.toString(totalTimeActive, new HoursFormatBuilder()));
    }

    private void isActive(final boolean isActive) {
        switchView.setChecked(isActive);
    }

    private void setUpClockIfActive(final UiTask task) {
        if (task.isActive()) {
            setUpClock(task);
        }
    }

    private void setUpClock(final UiTask task) {
        if (isAttachedToWindow()) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    receiveBroadcast(intent.getAction());
                }
            };
            registerBroadcastReceiver();
        }
    }

    private void receiveBroadcast(final String action) {
        if (action != null && action.equals(Intent.ACTION_TIME_TICK)) {
            onTick();
        }
    }

    private void onTick() {
        final long currentTime = System.currentTimeMillis();
        task.incrementActiveTime(currentTime - timeOfLastTick);
        timeOfLastTick = currentTime;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (hasActiveTask() && hasBroadcastReceiver()) {
            registerBroadcastReceiver();
        }
    }

    private boolean hasActiveTask() {
        return task != null && task.isActive();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (hasBroadcastReceiver()) {
            unregisterBroadcastReceiver();
        }
    }

    private boolean hasBroadcastReceiver() {
        return broadcastReceiver != null;
    }

    private void registerBroadcastReceiver() {
        getContext().registerReceiver(broadcastReceiver, INTENT_FILTER_TICK);
    }

    private void unregisterBroadcastReceiver() {
        getContext().unregisterReceiver(broadcastReceiver);
    }

    public void setOnCheckedChangeListener(final CompoundButton.OnCheckedChangeListener l) {
        switchView.setOnCheckedChangeListener(l);
    }
}
