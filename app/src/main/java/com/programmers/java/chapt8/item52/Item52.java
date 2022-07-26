package com.programmers.java.chapt8.item52;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Item52 {

	public static String classify(Set<?> set) {
		return "집합";
	}

	public static String classify(List<?> set) {
		return "리스트";
	}

	public static String classify(Collection<?> set) {
		return "그 외";
	}

	public static void main(String[] args) {
		Collection<?>[] collections = {
			new HashSet<String>(),
			new ArrayList<BigInteger>(),
			new HashMap<String,String>().values()
		};

		for (Collection<?> c : collections) {
			System.out.println(classify(c));
		}

	}

}
