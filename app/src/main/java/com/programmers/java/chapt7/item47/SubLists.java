package com.programmers.java.chapt7.item47;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SubLists {

	// (a, b, c)의 prefixes : (a), (a,b), (a,b,c)
	private static <E> Stream<List<E>> prefixes(List<E> list) {
		return IntStream.rangeClosed(1, list.size())
			.mapToObj(end -> list.subList(0, end));
	}

	// a의 suffixes : (a)
	// a,b의 suffixes : (a,b), (a)
	// a,b,c의 suffixes : (a,b,c), (b,c), (a)
	private static <E> Stream<List<E>> suffixes(List<E> list) {
		return IntStream.range(0, list.size())
			.mapToObj(start -> list.subList(start, list.size()));
	}

	// 입력 리스트의 모든 부분 리스트를 스트림으로 반환한다.
	// 3줄이면 충분하지만 입력 리스트 크기의 거듭제곱만큼 메모리를 차지하며 좋은 방법은 아니다.
	public static <E> Stream<List<E>> of(List<E> list) {
		return Stream.concat(Stream.of(Collections.emptyList()),
			prefixes(list).flatMap(SubLists::suffixes));
	}

	public static void main(String[] args) {
		List<String> list = Arrays.asList(args);
		SubLists.of(list).forEach(System.out::println);
	}

}
