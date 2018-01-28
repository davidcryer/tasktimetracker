package com.davidcryer.tasktimetracker.managecategories;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.davidcryer.argrules.EnforceRule;

import java.util.List;

class CategoriesFilter {
    private final static int NO_FILTER_INDEX = 0;
    private final static String NO_FILTER_CONTENT = "All categories";
    private final Context context;
    private final OnFilterChangeListener listener;
    private List<String> options;
    private int selected;

    CategoriesFilter(final Context context, final OnFilterChangeListener listener) {
        this.context = context;
        this.listener = listener;
    }

    void populate(final List<String> options, @Nullable final Spinner spinner) {
        EnforceRule.that(options != null, "adapter cannot be null");
        populate(spinner, () -> options(options));
    }

    private void options(final List<String> options) {
        setOptions(options);
        this.selected = 0;
    }

    void populate(final List<String> options, final Integer selected, @Nullable final Spinner spinner) {
        EnforceRule.that(options != null, "adapter cannot be null");
        populate(spinner, () -> options(options, selected));
    }

    private void options(final List<String> options, final int selected) {
        setOptions(options);
        this.selected = selected >= NO_FILTER_INDEX ? selected + 1 : selected;
    }

    private void populate(@Nullable final Spinner spinner, final Runnable setOptionsRunnable) {
        setOptionsRunnable.run();
        if (spinner != null) {
            setup(spinner);
        }
    }

    private void setOptions(final List<String> options) {
        options.add(NO_FILTER_INDEX, NO_FILTER_CONTENT);
        this.options = options;
    }

    void setup(@NonNull final Spinner spinner) {
        spinner.setAdapter(adapter());
        spinner.setSelection(selected);
        spinner.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorSelectedTextWhite(spinner);
                switch (position) {
                    case NO_FILTER_INDEX: {
                        listener.onFilterRemoved();
                    } break;
                    default: {
                        final int index = position > NO_FILTER_INDEX ? position - 1 : position;
                        listener.onFilterSelected(index);
                    } break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private static void colorSelectedTextWhite(final Spinner spinner) {
        final TextView selectedTextView = ((TextView) spinner.getSelectedView());
        if (selectedTextView != null) {
            selectedTextView.setTextColor(Color.WHITE);
        }
    }

    private ArrayAdapter<String> adapter() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    interface OnFilterChangeListener {
        void onFilterRemoved();
        void onFilterSelected(int i);
    }
}
