package com.davidcryer.tasktimetracker.managecategories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.davidcryer.tasktimetracker.R;
import com.davidcryer.tasktimetracker.common.TimeInterval;
import com.davidcryer.tasktimetracker.common.Timer;
import com.davidcryer.tasktimetracker.common.totalactivetime.FormatBuilder;
import com.davidcryer.tasktimetracker.common.totalactivetime.HoursFormatBuilder;
import com.davidcryer.tasktimetracker.common.totalactivetime.TotalActiveTimeFormatter;

public class TaskLayout extends LinearLayout {
    private final static FormatBuilder timeFormat = new HoursFormatBuilder();
    private final TextView titleView;
    private final TextView timeView;
    private final Switch switchView;
    private final Timer timer;
    private UiTask task;
    private Long timeOfLastTick;

    {
        timer = new Timer();
    }

    public TaskLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_task, this);
        titleView = findViewById(R.id.title);
        timeView = findViewById(R.id.time);
        switchView = findViewById(R.id.selector);
    }

    private void onTick() {
        final long currentTime = System.currentTimeMillis();
        task.incrementActiveTime(currentTime - timeOfLastTick);
        timeOfLastTick = currentTime;
        totalTimeActive(task.getTotalTimeActive(), task.isActive());
    }

    void task(final UiTask task) {
        this.task = task;
        title(task.getTitle());
        totalTimeActive(task.getTotalTimeActive(), task.isActive());
        isActive(task.isActive());
        setUpClock(task);
    }

    private void title(final String title) {
        titleView.setText(title);
    }

    private void totalTimeActive(final long totalTimeActive, final boolean isActive) {
        timeView.setText(TotalActiveTimeFormatter.toString(totalTimeActive, isActive, timeFormat));
    }

    private void isActive(final boolean isActive) {
        switchView.setChecked(isActive);
    }

    private void setUpClock(final UiTask task) {
        if (isAttachedToWindow()) {
            if (task.isActive()) {
                scheduleTimer();
            } else {
                cancelTimer();
            }
        }
    }

    private void scheduleTimer() {
        timeOfLastTick = System.currentTimeMillis();
        timer.schedule(this::onTick, TimeInterval.MILLIS_IN_SECOND, TimeInterval.MILLIS_IN_SECOND);
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (hasActiveTask()) {
            scheduleTimer();
        }
    }

    private boolean hasActiveTask() {
        return task != null && task.isActive();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelTimer();
    }

    public void setOnCheckedChangeListener(final CompoundButton.OnCheckedChangeListener l) {
        switchView.setOnCheckedChangeListener(l);
    }
}
