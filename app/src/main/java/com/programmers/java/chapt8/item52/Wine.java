package com.programmers.java.chapt8.item52;

import java.util.List;

public class Wine {
	String name() {
		return "포도주";
	}
}

class SparklingWine extends Wine {
	@Override
	String name() {
		return "발포성 포도주";
	}
}

class Chapagne extends Wine {
	@Override
	String name() {
		return "샴페인";
	}
}

class Overriding {
	public static void main(String[] args) {
		List<Wine> wineList = List.of(
			new Wine(), new SparklingWine(), new Chapagne()
		);

		for (Wine wine : wineList) {
			System.out.println(wine.name());
		}
	}
}