//--------------------------------------------------
// Interface Logging
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

@FunctionalInterface
public interface Logging {
    String getLogTag();

    @NotNull
    @Deprecated
    static Logger getLoggerGlobal() {
        return LogSettings.getGlobal();
    }

    @NotNull
    static Logger getLogger(@NotNull String tag) {
        return LogSettings.getLogger(tag);
    }

    @NotNull
    static Logger getLogger(@NotNull String tag, Level level) {
        return LogSettings.getLogger(tag, level);
    }

    @NotNull
    static Logger getLogger(@NotNull Logging object) {
        return getLogger(object.getLogTag());
    }

    @NotNull
    static Logger getLogger(@NotNull Logging object, Level level) {
        return getLogger(object.getLogTag(), level);
    }

    @NotNull
    default Logger getLogger() {
        return getLogger(this, getLoggerDefaultLevel());
    }

    @Nullable
    default Level getLoggerDefaultLevel() { return null; }
}
