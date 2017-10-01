package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.ObjectUtils;

public class Epic {
    private String title;
    private String note;

    public Epic(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public String title() {
        return title;
    }

    private void title(final String title) {
        this.title = title;
    }

    public String note() {
        return note;
    }

    private void note(final String note) {
        this.note = note;
    }

    public Writer writer() {
        return new Writer(this);
    }

    public static class Writer {
        private final Epic epic;
        private String title;
        private boolean titleChanged;
        private String note;
        private boolean noteChanged;

        private Writer(Epic epic) {
            this.epic = epic;
        }

        public Writer title(final String title) {
            titleChanged = !ObjectUtils.equalAllowNull(title, this.title);
            this.title = title;
            return this;
        }

        public Writer note(final String note) {
            noteChanged = !ObjectUtils.equalAllowNull(note, this.note);
            this.note = note;
            return this;
        }

        public void commit() {
            writeTitle();
            writeNote();
        }

        private void writeTitle() {
            if (titleChanged) {
                epic.title(title);
            }
        }

        private void writeNote() {
            if (noteChanged) {
                epic.note(note);
            }
        }
    }
}
