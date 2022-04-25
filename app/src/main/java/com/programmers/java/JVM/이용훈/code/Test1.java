package com.programmers.java.JVM.이용훈.code;

public class Test1 {
    public static void main(String[] args) {

        Class c = Test1.class;

        //Application/System classloader will load this class
        System.out.println(c.getClassLoader());

        //If we print the classloader name of String, it will print null because it is an
        //in-built class which is present in rt.jar, Bootstrap classloader loads it.
        System.out.println(String.class.getClassLoader());
    }
}
