package com.davidcryer.tasktimetracker.managestories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidcryer.tasktimetracker.R;

public class TaskLayout extends LinearLayout {
    private final TextView titleView;

    public TaskLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.item_manage_stories_task, this);
        titleView = findViewById(R.id.title);
    }

    public void task(final UiTask task) {
        title(task.getTitle());
    }

    private void title(final String title) {
        titleView.setText(title);
    }
}
