//--------------------------------------------------
// Class LimitedLoopQueue
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.lang;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings({"unchecked", "unused"})
public class UnlimitedLoopQueue<E> implements Queue<E>, Cloneable, Serializable {
    @Nullable
    transient final private Object[] container;

    private int insertPos = 0;
    private int headElementPos = 0;

    public UnlimitedLoopQueue(int size) {
        this.container = new Object[size];
    }

    private UnlimitedLoopQueue(@Nullable Object[] container, int insertPos, int headElementPos) {
        this.container = container;
        this.insertPos = insertPos;
        this.headElementPos = headElementPos;
    }

    @Override
    public int size() {
        if (insertPos >= headElementPos)
            return insertPos - headElementPos;
        else
            return container.length - (headElementPos - insertPos);
    }

    @Override
    public boolean isEmpty() {
        return insertPos == headElementPos;
    }

    @Override
    public boolean contains(@Nullable Object o) {
        if (o == null)
            return false;

        for (int i = 0; i < size(); i++) {
            int pos = elementIndexOf(i);
            if (o.equals(container[pos]))
                return true;
        }

        return false;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int iterHeadElementPos = headElementPos;

            @Override
            public boolean hasNext() {
                return insertPos != headElementPos;
            }

            @Override
            public E next() {
                E head = (E) container[iterHeadElementPos];
                iterHeadElementPos = (iterHeadElementPos + 1) % container.length;

                return head;
            }
        };
    }

    @NotNull
    @Override
    public E[] toArray() {
        Object[] result = new Object[size()];

        for (int i = 0; i < size(); i++) {
            int pos = elementIndexOf(i);
            result[i] = container[pos];
        }

        return (E[]) result;
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        Object[] result;
        int size = size();

        if (a.length >= size)
            result = a;
        else
            result = new Object[size()];


        for (int i = 0; i < size(); i++) {
            int pos = elementIndexOf(i);
            result[i] = container[pos];
        }

        return (T[]) result;
    }

    private void nextElementPos() {
        container[headElementPos] = null;
        headElementPos = (headElementPos + 1) % container.length;
    }

    private void nextInsertPos() {
        insertPos = (insertPos + 1) % container.length;
    }

    @Contract(value = "null -> fail; !null -> param1", pure = true)
    private <T> @NotNull T assertElementNotNull(T obj) {
        if (obj == null)
            throw new NoSuchElementException();
        else
            return obj;
    }

    private int elementIndexOf(int offset) {
        return (headElementPos + offset) % container.length;
    }

    @Override
    public boolean add(E e) {
        container[insertPos] = e;

        if (((insertPos + 1) % container.length) == headElementPos) {
            nextElementPos();
        }

        nextInsertPos();
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false; //TODO
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return false; //TODO
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false; //TODO
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false; //TODO
    }

    @Override
    public void clear() {
        for (int i = 0; i < size(); i++) {
            container[elementIndexOf(i)] = null;
        }
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        return assertElementNotNull(poll());
    }

    @Override
    public E poll() {
        E head = peek();
        nextElementPos();

        return head;
    }

    @Override
    public E element() {
        return assertElementNotNull(peek());
    }

    @Override
    public E peek() {
        return (E) container[headElementPos];
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public UnlimitedLoopQueue<E> clone() {
        return new UnlimitedLoopQueue<>(container.clone(), insertPos, headElementPos);
    }

    @Override
    public String toString() {
        return "UnlimitedLoopQueue{" +
                "container=" + Arrays.toString(container) +
                ", insertPos=" + insertPos +
                ", headElementPos=" + headElementPos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnlimitedLoopQueue<?> that = (UnlimitedLoopQueue<?>) o;
        return insertPos == that.insertPos &&
                headElementPos == that.headElementPos &&
                Arrays.equals(container, that.container);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(insertPos, headElementPos);
        result = 31 * result + Arrays.hashCode(container);
        return result;
    }
}
