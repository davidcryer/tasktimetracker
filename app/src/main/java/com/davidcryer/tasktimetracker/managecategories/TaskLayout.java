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

public class TaskLayout extends LinearLayout implements UiTask.View {
    private final static FormatBuilder timeFormat = new HoursFormatBuilder();
    private final TextView titleView;
    private final TextView timeView;
    private final Switch switchView;
    private UiTask task;

    public TaskLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_task, this);
        titleView = findViewById(R.id.title);
        timeView = findViewById(R.id.time);
        switchView = findViewById(R.id.selector);
    }

    void task(final UiTask task) {
        this.task = task;
        task.register(this);
    }

    @Override
    public void title(final String title) {
        titleView.setText(title);
    }

    @Override
    public void totalTimeActive(final long totalTimeActive, final boolean isActive) {
        timeView.setText(TotalActiveTimeFormatter.toString(totalTimeActive, isActive, timeFormat));
    }

    @Override
    public void activationState(final boolean isActive) {
        switchView.setChecked(isActive);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        task.attachedToWindow(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        task.detachedFromWindow(this);
    }

    public void setOnCheckedChangeListener(final CompoundButton.OnCheckedChangeListener l) {
        switchView.setOnCheckedChangeListener(l);
    }
}
