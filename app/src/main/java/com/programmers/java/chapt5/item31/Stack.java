package com.programmers.java.chapt5.item31;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class Stack<E> {
    private List<E> elements;
    private int size = 0;

    public Stack() {
        this.elements = new ArrayList<>();
    }

    public void push(E o) {
        elements.add(o);
        size++;
    }

    public <E> E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        E element = (E)elements.get(size);
        elements.remove(size--);
        return element;
    }

    public void pushAll(Iterable<E> src) {
        for (E e : src) {
            push(e);
        }
    }

    public boolean isEmpty() {
        return elements.size() == 0;
    }
}
