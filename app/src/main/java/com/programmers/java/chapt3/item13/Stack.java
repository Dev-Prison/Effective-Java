package com.programmers.java.chapt3.item13;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack implements Cloneable {
	private A[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public Stack() {
		System.out.println("---------------");
		System.out.println("Stack constructor");
		this.elements = new A[DEFAULT_INITIAL_CAPACITY];
	}

	public A[] getElements() {
		return elements;
	}

	public int getSize() {
		return size;
	}

	public void push(A e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public A pop() {
		if(size == 0) {
			throw new EmptyStackException();
		}
		A result = elements[--size];
		elements[size] = null;
		return result;
	}

	private void ensureCapacity() {
		if(elements.length == size) {
			elements = Arrays.copyOf(elements,2 * size + 1);
			System.out.println(elements.length);
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		int i = 0;
		while (elements[i] != null) {
			stringBuilder.append(elements[i].num);
			stringBuilder.append(",");
			i++;
		}

		return stringBuilder.toString();
	}

	@Override
	public Stack clone() {
		try {
			return (Stack) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}