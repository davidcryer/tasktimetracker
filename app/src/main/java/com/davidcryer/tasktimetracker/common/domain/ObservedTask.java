package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.domain.exceptions.AlreadyActiveException;
import com.davidcryer.tasktimetracker.common.domain.exceptions.AlreadyInactiveException;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ObservedTask extends Task {
    private final Set<WeakReference<Observer>> observers = new HashSet<>();

    ObservedTask (final UUID id, final String title, final String note, final OngoingSession ongoingSession, final List<FinishedSession> finishedSessions, final ActivatedTaskRegister activatedTaskRegister) throws TaskArgResults.Exception {
        super(id, title, note, ongoingSession, finishedSessions, activatedTaskRegister);
    }

    public void observe(final Observer observer) {
        observers.add(new WeakReference<>(observer));
    }

    @Override
    void title(String title) {
        super.title(title);
        notifyObservers(this::notifyOfTitleChange);
    }

    private void notifyOfTitleChange(final Observer observer) {
        observer.onTitleChange(title());
    }

    @Override
    void note(String note) {
        super.note(note);
        notifyObservers(this::notifyOfNoteChange);
    }

    private void notifyOfNoteChange(final Observer observer) {
        observer.onNoteChange(note());
    }

    @Override
    public void onActivate() throws AlreadyActiveException {
        super.onActivate();
        notifyObservers(this::notifyOfActivation);
    }

    private void notifyOfActivation(final Observer observer) {
        observer.onActivated();
    }

    @Override
    public void onDeactivate() throws AlreadyInactiveException {
        super.onDeactivate();
        notifyObservers(this::notifyOfDeactivation);
    }

    private void notifyOfDeactivation(final Observer observer) {
        observer.onDeactivated();
    }

    private void notifyObservers(final IteratorCallback callback) {
        final Iterator<WeakReference<Observer>> itr = observers.iterator();
        while (itr.hasNext()) {
            final Observer observer = itr.next().get();
            if (observer == null) {
                itr.remove();
            } else {
                callback.observer(observer);
            }
        }
    }

    interface Observer {
        void onTitleChange(String title);
        void onNoteChange(String note);
        void onActivated();
        void onDeactivated();
    }

    interface IteratorCallback {
        void observer(Observer observer);
    }
}
