//--------------------------------------------------
// Class CallerClass
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.lang;

import org.jetbrains.annotations.NotNull;

public class CallerClass extends SecurityManager {
    private static final CallerClass INSTANCE = new CallerClass();

    @NotNull
    public static Class[] get() {
        return INSTANCE.getClassContext();
    }

    @NotNull
    public static Class get(int depth) {
        return INSTANCE.getClassContext()[depth];
    }
}
