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

    static void useAsDefaultLogger() {

    }

    @NotNull
    static Logger getLoggerGlobal() {
        return Logger.getGlobal();
    }

    @NotNull
    static Logger getLogger(@NotNull String tag) {
        return Logger.getLogger(tag);
    }

    @NotNull
    static Logger getLogger(@NotNull Logging object) {
        return Logger.getLogger(object.getLogTag());
    }

    @NotNull
    default Logger getLogger() {
        return getLogger(this);
    }
}
