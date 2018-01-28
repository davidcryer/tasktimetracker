package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Rule;
import com.davidcryer.tasktimetracker.common.ObjectUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Task implements OngoingTaskRegister.Task {
    private final static String ILLEGAL_ID_MESSAGE = "id cannot be null";
    private final static String ILLEGAL_TITLE_MESSAGE = "title cannot be null";
    private final static String ILLEGAL_ONGOING_SESSION_MESSAGE = "ongoing session cannot be finished";
    private final OngoingTaskRegister ongoingTaskRegister;
    private final UUID id;
    private String title;
    private String note;
    private OngoingSession ongoingSession;
    private List<FinishedSession> finishedSessions;
    private final Set<WeakReference<OngoingStatusListener>> ongoingStatusListeners;
    private OnChangeListener onChangeListener;

    private Task(final UUID id, final String title, final String note, final OngoingSession ongoingSession, final List<FinishedSession> finishedSessions, final OngoingTaskRegister ongoingTaskRegister) throws TaskArgRules.Exception {
        new TaskArgRulesBuilder().id(idArg(id)).title(titleArg(title)).ongoingSession(ongoingSessionArg(ongoingSession)).args().enforce();
        this.id = id;
        this.title = title;
        this.note = note;
        this.ongoingSession = ongoingSession;
        this.finishedSessions = finishedSessions;
        this.ongoingTaskRegister = ongoingTaskRegister;
        ongoingStatusListeners = new HashSet<>();
    }

    static Task create(final String title, final String note, final OngoingTaskRegister ongoingTaskRegister) throws TaskArgRules.Exception {
        return new Task(UUID.randomUUID(), title, note, null, null, ongoingTaskRegister);
    }

    static Task inflate(final UUID id, final String title, final String note, final OngoingSession ongoingSession, final List<FinishedSession> finishedSessions, final OngoingTaskRegister ongoingTaskRegister) throws TaskArgRules.Exception {
        final Task task = new Task(id, title, note, ongoingSession, finishedSessions, ongoingTaskRegister);
        if (task.isOngoing()) {
            ongoingTaskRegister.setUp(task);
        }
        return task;
    }

    private static Rule idArg(final UUID id) {
        return new Rule(id != null, ILLEGAL_ID_MESSAGE);
    }

    private static Rule titleArg(final String title) {
        return new Rule(title != null, ILLEGAL_TITLE_MESSAGE);
    }

    private static Rule ongoingSessionArg(final OngoingSession ongoingSession) {
        return new Rule(ongoingSession == null || !ongoingSession.isFinished(), ILLEGAL_ONGOING_SESSION_MESSAGE);
    }

    public void start() throws AlreadyStartedException {
        ongoingTaskRegister.register(this);
    }

    public void stop() throws AlreadyStoppedException {
        ongoingTaskRegister.unregister(this);
    }

    private void addFinishedSession(final FinishedSession session) {
        if (finishedSessions == null) {
            finishedSessions = new LinkedList<>();
        }
        finishedSessions.add(session);
    }

    public boolean isOngoing() {
        return ongoingSession != null;
    }

    public long expendedTime() {
        return ongoingSessionDuration() + finishedSessionsDuration();
    }

    private long ongoingSessionDuration() {
        return ongoingSession == null ? 0L : ongoingSession.duration();
    }

    private long finishedSessionsDuration() {
        long expended = 0L;
        if (finishedSessions != null) {
            for (final FinishedSession finishedSession : finishedSessions) {
                expended += finishedSession.duration();
            }
        }
        return expended;
    }

    public boolean deleteSession(final UUID sessionId) {
        if (finishedSessions != null) {
            for (final Iterator<FinishedSession> itr = finishedSessions.iterator(); itr.hasNext(); ) {
                if (itr.next().id().equals(sessionId)) {
                    itr.remove();
                    notifyChanged();
                    return true;
                }
            }
        }
        return false;
    }

    public List<FinishedSession> finishedSessions() {
        return finishedSessions == null ? new ArrayList<>() : new ArrayList<>(finishedSessions);
    }

    void addOngoingStatusListener(final OngoingStatusListener listener) {
        if (listener != null) {
            ongoingStatusListeners.add(new WeakReference<>(listener));
        }
    }

    void onChangeListener(final OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    private void notifyChanged() {
        if (onChangeListener != null) {
            onChangeListener.taskChanged(this);
        }
    }

    @Override
    public void onRegister() throws AlreadyStartedException {
        if (isOngoing()) {
            throw new AlreadyStartedException();
        }
        ongoingSession = new OngoingSession();
        notifyOngoingTaskListener(listener -> listener.onStart(this));
        notifyChanged();
    }

    @Override
    public void onUnregister() throws AlreadyStoppedException {
        if (!isOngoing()) {
            throw new AlreadyStoppedException();
        }
        addFinishedSession(ongoingSession.stop());
        ongoingSession = null;
        notifyOngoingTaskListener(listener -> listener.onStop(this));
        notifyChanged();
    }

    private void notifyOngoingTaskListener(final NotifyOngoingStatusListenerAction action) {
        for (final Iterator<WeakReference<OngoingStatusListener>> itr = ongoingStatusListeners.iterator(); itr.hasNext();) {
            final OngoingStatusListener listener = itr.next().get();
            if (listener == null) {
                itr.remove();
            } else {
                action.perform(listener);
            }
        }
    }

    private interface NotifyOngoingStatusListenerAction {
        void perform(OngoingStatusListener listener);
    }

    DbTask toDbTask() {
        return new DbTask(id, title, note, dbOngoingSession(), DbMapper.dbFinishedSessions(finishedSessions));
    }

    private DbOngoingSession dbOngoingSession() {
        return ongoingSession == null ? null : ongoingSession.toDbOngoingSession();
    }

    public UUID id() {
        return id;
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
        private final Task task;
        private String title;
        private boolean titleChanged;
        private String note;
        private boolean noteChanged;

        private Writer(Task task) {
            this.task = task;
        }

        public Writer title(final String title) {
            titleChanged = !ObjectUtils.equalAllowNull(title, task.title());
            this.title = title;
            return this;
        }

        public Writer note(final String note) {
            noteChanged = !ObjectUtils.equalAllowNull(note, task.note());
            this.note = note;
            return this;
        }

        public void commit() throws TaskArgRules.Exception {
            inspectInput();
            writeTitle();
            writeNote();
            notifyChanged();
        }

        private void inspectInput() throws TaskArgRules.Exception {
            args().enforce();
        }

        private TaskArgRules args() {
            final TaskArgRulesBuilder builder = new TaskArgRulesBuilder();
            if (titleChanged) {
                builder.title(Task.titleArg(title));
            }
            return builder.args();
        }

        private void writeTitle() {
            if (titleChanged) {
                task.title(title);
            }
        }

        private void writeNote() {
            if (noteChanged) {
                task.note(note);
            }
        }

        private void notifyChanged() {
            if (titleChanged || noteChanged) {
                task.notifyChanged();
            }
        }
    }

    public interface OngoingStatusListener {
        void onStart(Task task);
        void onStop(Task task);
    }

    interface OnChangeListener {
        void taskChanged(Task task);
    }
}
