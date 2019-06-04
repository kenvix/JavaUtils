package com.kenvix.utils.lang;//--------------------------------------------------
// Class Pair
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import java.io.Serializable;
import java.nio.file.Path;

public class Pair<T, U> implements Cloneable, Serializable {
    public final T first;
    public final U second;


    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair))
            return false;

        Pair object = (Pair) obj;

        return object.first.equals(first) && object.second.equals(second);
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", first, second);
    }

    @Override
    protected Pair clone() throws CloneNotSupportedException {
        return  (Pair) super.clone();
    }
}
