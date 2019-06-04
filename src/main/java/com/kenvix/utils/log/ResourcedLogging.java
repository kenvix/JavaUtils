//--------------------------------------------------
// Interface ResourcedLogging
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public interface ResourcedLogging extends Logging {
    String getLogResourceBundleName();

    @NotNull
    static Logger getLogger(@NotNull ResourcedLogging object) {
        return Logger.getLogger(object.getLogTag(), object.getLogResourceBundleName());
    }

    @Override
    default @NotNull Logger getLogger() {
        return getLogger(this);
    }
}
