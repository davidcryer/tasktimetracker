package com.davidcryer.tasktimetracker.managecategories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidcryer.tasktimetracker.R;

public class CategoryLayout extends LinearLayout {
    private final TextView titleView;
    private final TextView noteView;
    private final View addTaskView;
    private final View divider;

    public CategoryLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_category, this);
        titleView = findViewById(R.id.title);
        noteView = findViewById(R.id.note);
        addTaskView = findViewById(R.id.add);
        divider = findViewById(R.id.divider);
    }

    void category(final UiCategory category) {
        title(category.getTitle());
        note(category.getNote());
    }

    private void title(final String title) {
        titleView.setText(title);
    }

    private void note(final String note) {
        noteView.setText(note);
    }

    void showDivider() {
        divider.setVisibility(VISIBLE);
    }

    void hideDivider() {
        divider.setVisibility(GONE);
    }

    void setOnClickAddTaskListener(final OnClickListener l) {
        addTaskView.setOnClickListener(l);
    }
}
