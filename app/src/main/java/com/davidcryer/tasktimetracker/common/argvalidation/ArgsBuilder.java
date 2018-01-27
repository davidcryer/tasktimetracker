package com.davidcryer.tasktimetracker.common.argvalidation;

public interface ArgsBuilder<T extends IllegalArgsException.Args> {
    T args();
}
