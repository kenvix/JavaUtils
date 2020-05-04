//--------------------------------------------------
// Class CallerClass
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.lang;

import org.jetbrains.annotations.NotNull;

public class CallerClass extends SecurityManager {
    private static final CallerClass INSTANCE = new CallerClass();

    private CallerClass() {
    }

    @NotNull
    public static Class[] get() {
        return INSTANCE.getClassContext();
    }

    @NotNull
    public static Class get(int depth) {
        return INSTANCE.getClassContext()[depth];
    }
}
