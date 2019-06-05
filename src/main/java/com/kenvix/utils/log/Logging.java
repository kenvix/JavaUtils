//--------------------------------------------------
// Interface Logging
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

@FunctionalInterface
public interface Logging {
    String getLogTag();

    @NotNull
    static Logger getLoggerGlobal() {
        return LogSettings.getGlobal();
    }

    @NotNull
    static Logger getLogger(@NotNull String tag) {
        return LogSettings.getLogger(tag);
    }

    @NotNull
    static Logger getLogger(@NotNull Logging object) {
        return LogSettings.getLogger(object.getLogTag());
    }

    @NotNull
    default Logger getLogger() {
        return getLogger(this);
    }
}
