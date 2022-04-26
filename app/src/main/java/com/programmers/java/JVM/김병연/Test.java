package com.programmers.java.JVM.김병연;

public class Test {
	public static void main(String[] args) {
		String s1=new String("AAA");
		String s2=new String("BBB");
		System.out.println(s1 +", "+s2);
		new Test().call(s1,s2);
		System.out.println(s1 +", "+s2);

		System.out.println("===================");


		Person prog = new Person("prog", 25);
		System.out.println("prog = " + prog);
		new Test().call(prog);
		System.out.println("prog = " + prog);
	}

	public void call(String a,String b){
		a=new String("dasdasd");
		b=new String("dasdasd");
	}

	public void call(Person person){
		person.age=112;
		person.name=new String("update");
	}
}

class Person{
	String name;
	int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
