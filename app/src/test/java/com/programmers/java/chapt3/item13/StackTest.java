package com.programmers.java.chapt3.item13;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StackTest {
	@Test
	@DisplayName("Stack super.clone() 반환")
	public void cloneTest(){
	    //given
		Stack stack = new Stack();

		stack.push(new A(10));
		stack.push(new A(20));

		//when
		Stack clone = stack.clone();

		clone.push(new A(30));
		//then

		Assertions.assertNotEquals(stack,clone);
		Assertions.assertEquals(stack.getElements(),clone.getElements());
		Assertions.assertNotEquals(stack.getSize(),clone.getSize());

		System.out.println(Arrays.toString(stack.getElements()));
		System.out.println(Arrays.toString(clone.getElements()));

		/**
		 * Note : 위와 같이 테스트는 성공한다. Stack 클래스의 int size 필드[primitive type] 는 기본타입으로 값이 정상적으로 복사되어 서로 다른 값을 갖지만,
		 * 	참조 필드인 elements 의 경우 같은 주소를 가지게 되어 변경사항이 원본에도 영향을 끼친다.
		 */
	}
}