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
    private final View divider;
    private final View clicker;

    public CategoryLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_category, this);
        titleView = findViewById(R.id.title);
        noteView = findViewById(R.id.note);
        divider = findViewById(R.id.divider);
        clicker = this;
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

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        if (clicker == this) {
            super.setOnClickListener(l);
        } else {
            clicker.setOnClickListener(l);
        }
    }
}
