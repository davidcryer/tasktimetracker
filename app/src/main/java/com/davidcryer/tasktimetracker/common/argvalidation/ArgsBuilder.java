package com.davidcryer.tasktimetracker.common.argvalidation;

public interface ArgsBuilder<E extends IllegalArgsException, A extends IllegalArgsException.Args<E>> {
    A args();
}
