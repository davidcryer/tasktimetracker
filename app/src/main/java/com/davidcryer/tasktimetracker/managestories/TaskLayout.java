package com.davidcryer.tasktimetracker.managestories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidcryer.tasktimetracker.R;


public class TaskLayout extends LinearLayout {
    private final TextView titleView;
    private final TextView noteView;

    public TaskLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        titleView = findViewById(R.id.title);
        noteView = findViewById(R.id.note);
    }

    public void task(final UiTask task) {

    }
}
