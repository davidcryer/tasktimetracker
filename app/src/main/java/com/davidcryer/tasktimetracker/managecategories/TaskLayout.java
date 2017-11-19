package com.davidcryer.tasktimetracker.managecategories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidcryer.tasktimetracker.R;

public class TaskLayout extends LinearLayout {
    private final TextView titleView;
    private final View clicker;

    public TaskLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_task, this);
        titleView = findViewById(R.id.title);
        clicker = this;
    }

    void task(final UiTask task) {
        title(task.getTitle());
    }

    private void title(final String title) {
        titleView.setText(title);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        if (clicker == this) {
            super.setOnClickListener(l);
        } else {
            clicker.setOnClickListener(l);
        }
    }
}
