package com.davidcryer.tasktimetracker.managecategories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;

import java.util.List;

class CategoriesFilter implements AdapterView.OnItemClickListener {
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
        Arg.enforce(options != null, "adapter cannot be null");
        populate(spinner, new Runnable() {
            @Override
            public void run() {
                options(options);
            }
        });
    }

    private void options(final List<String> options) {
        setOptions(options);
        this.selected = 0;
    }

    void populate(final List<String> options, final Integer selected, @Nullable final Spinner spinner) {
        Arg.enforce(options != null, "adapter cannot be null");
        populate(spinner, new Runnable() {
            @Override
            public void run() {
                options(options, selected);
            }
        });
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
        spinner.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case NO_FILTER_INDEX: {
                listener.onFilterRemoved();
            } break;
            default: {
                final int index = i > NO_FILTER_INDEX ? i - 1 : i;
                listener.onFilterSelected(index);
            } break;
        }
    }

    private ArrayAdapter<String> adapter() {
        return new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, options);
    }

    interface OnFilterChangeListener {
        void onFilterRemoved();
        void onFilterSelected(int i);
    }
}
