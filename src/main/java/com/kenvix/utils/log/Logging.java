//--------------------------------------------------
// Class Logging
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Logging {
    default Logger getLogger() {
        if (getLogTag() != null)
            return getLogger(getLogTag());
        else
            return getLogger(this.getClass());
    }

    static Logger getLogger(String tag) {
        return LoggerFactory.getLogger(tag);
    }

    static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    default String getLogTag() {
        return null;
    }
}