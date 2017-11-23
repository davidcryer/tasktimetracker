package com.davidcryer.tasktimetracker.managecategories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.davidcryer.tasktimetracker.R;

public class AddTaskLayout extends LinearLayout {

    public AddTaskLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_add_task, this);
    }
}
