package com.programmers.java.chapt3.item13;

public class Main {

	public static void main(String[] args) {
		A a = new A(12);
		A clone = a.clone();
		String s = clone.toString();
		System.out.println("A.num = " + clone.toString());
	}
}

class A implements Cloneable {
	int num;

	public A() {
		System.out.println("---------------------");
		System.out.println("A constructor");
		System.out.println("---------------------");
	}

	public A(int num) {
		System.out.println("---------------------");
		System.out.println("A constructor");
		System.out.println("---------------------");
		this.num = num;
	}

	@Override
	public A clone() {
		try {
			System.out.println("---------------------");
			System.out.println("A Clone");
			System.out.println("---------------------");
			return (A)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public String toString() {
		return "A{" +
				"num=" + num +
				'}';
	}
}
