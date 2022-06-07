package com.programmers.java.chapt5.item33;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class main {
	public static void main(String[] args) {
		Favorites favorites = new Favorites();
		favorites.putFavorite(String.class, "Java");
		favorites.putFavorite(Integer.class, 0x123abcd);
		favorites.putFavorite(Class.class, Favorites.class);

		String favoriteString = favorites.getFavorite(String.class);
		int favoriteInteger = favorites.getFavorite(Integer.class);
		Class<?> favoriteClass = favorites.getFavorite(Class.class);

		System.out.printf("%s %x %s%n",
			favoriteString, favoriteInteger, favoriteClass.getName());


		try {
			favorites.putFavorite((Class)Integer.class, "Integer의 인스턴스가 아님");
		} catch (ClassCastException e) {
			System.out.println("cast exception");
		}

		favorites.getFavorite(Integer.class);// ClassCastException
	}

}

class Favorites {
	private Map<Class<?>, Object> favorite = new HashMap<>();

	public <T> void putFavorite(Class<T> type, T instance) {
		favorite.put(Objects.requireNonNull(type), type.cast(instance));
	}

	public <T> T getFavorite(Class<T> type) {
		return type.cast(favorite.get(type));
	}

}
