package com.kenvix.utils.lang;

import java.io.Serializable;

public class Holder<T> implements Cloneable, Serializable {
    public final T t;

    public Holder(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return t.toString();
    }

    @Override
    public int hashCode() {
        return t.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Holder && ((Holder) obj).t.equals(obj);
    }

    @Override
    protected Holder clone() throws CloneNotSupportedException {
        return (Holder) super.clone();
    }
}