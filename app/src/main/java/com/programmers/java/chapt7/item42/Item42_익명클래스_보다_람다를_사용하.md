[노션링크](https://morning-paprika-8fa.notion.site/Item42-badd9f20079c4286947ed7843eca011f)

# Item42 - 익명 클래스보다 람다를 사용하라

## 🌧  소개

- 예전에는 자바에서 함수 타입을 표현할 때 추상메서드를 하나만 담은 인터페이스(드물게는 추상클래스)를 사용했었다.
    - 이런 인터페이스의 인스턴스를 “함수객체"라고 하여, 특정 함수나 동작을 나타내는 데 사용했다.
    - 1997년 JDK1.1이 등장하면서 함수 객체를 만드는 주요 수단은 익명클래스(아이템 24)가 되었다.

```java
// 예시 코드로 문자열을 길이순으로 정렬하는데, 정렬을 위한 비교함수로 익명클래스를 사용한다.
Collections.sort(words, new Comparator<String>() {
	public int compare(String s1, String s2) {
		return Integer.compare(s1.length(), s2.length());
	}
});
```

- 전략패턴처럼, 함수 객체를 사용하는 과거 객체 지향 디자인 패턴에는 익명 클래스면 충분했다.
    - 위의 코드에서 Comparator 인터페이스가 정렬을 담당하는 추상 전략을 뜻하며 문자열을 정렬하는 구체적인 전략을 익명 클래스로 구현했다.
- **하지만 익명 클래스 방식은 코드가 너무 길기 때문에 자바는 함수형 프로그래밍에 적합하지 않았다.**

## ☄️  람다식의 등장

- 자바 8에 와서 추상 메서드 하나짜리 인터페이스는 특별한 의미를 인정받아 **특별한 대우**를 받게 되었다.
- 지금은 함수형 인터페이스라 부르는 이 인터페이스들의 인스턴스를 람다식(lambda exression)을 사용해 만들 수 있게 된것이다.
    - 람다는 함수나 익명 클래스와 개념은 비슷하지만, 코드는 훨씬 간결하다.

```java
// 다음은 익명 클래스를 사용한 앞의 코드를 람다 방식으로 바꾼 모습이다. 
// 자질구레한 코드들이 사라지고 어떤 동작을 하는지가 명확하게 드러남 !
Collections.sort(word, 
			(s1,s2) -> 	Integer.compare(s1.length(), s2.length()));
```

- 여기서 람다, 매개변수 (s1, s2) 반환값의 타입은 각자 (Comparator<String>), String, int 지만 위의 코드에서는 언급이 없다. **컴파일러가 문맥을 살펴 타입을 추론해준 것이다.**
    - 컴파일러가 타입을 결정하지 못할 수도 있는데, 그럴 때는 프로그래머가 직접 명시해야 한다.
    - 이런 경우는 제네릭의 로타입 사용에서 발생할 수 있다.
        - 로타입 제네릭 메서드와 람다식을 같이 활용한다면 컴파일러가 타입추론하기 힘들어진다.
        - 그 이유는 컴파일러가 타임을 추론하는데 필요한 정보를 제네릭에서 얻기 때문이다.

    ```java
    List words = Arrays.asList("김병연","김수미","김형욱","이연우","이용훈");
    Collections.sort(words, 
    				(s1, s2) -> Integer.compare(s1.length(), s2.length())); // 에러발생
    // 굳이 raw 타입을 사용해야한다면 형변환을 하나하나 해줘야한다.. 
    ```

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/73a1e125-b159-4007-9d3e-0bd6117f5fb4/Untitled.png)

- **타입을 명시해야 코드가 더 명확할 때만 제외하고는, 람다의 모든 매개변수 타입은 생략하도록 하자.**

## ⚡️  생성자 메서드 등장

- 람다의 사용법을 알게 되었다면 생성자 메서드를 통해 더 코드를 깔끔하게 작성할 수 있다.

```java
//자바 8때 List 인터페이스에 추가된 sort 메서드를 이용하면 더욱 짧아진다.
Collections.sort(words,comparingInt(String::length));
```

```java
// 람다를 언어 차원에서 지원하면서 기존에는 적합하지 않았던 곳에서도 객체를 실용적으로 사용할 수 있게 되었다.
words.sort(comparingInt(String::length));
```

### 👀  Item 34 예제 변형시키기

- 아이템 34의 Operation 열거 타입의 예로 추상 메서드를 선언하고 이를 각 열거 타입마다 재정의 함으로써, 각 타입마다 메서드가 다르게 동작하도록 구현할 수 있었다.

```java
public enum Operation {
	PLUS("+") {
		public double apply(double x, double y) { return x + y; }
	},
	... ~~~
	
	private final String symbol;
	
	Operation(String symbol) { this.symbol = symbol; }	
	
	@Ovveride
	public String toString() { return symbol; }
	
	public abstract double apply(double x, double y);
}
```

- **람다를 사용하면 열거 타입의 인스턴스 필드를 이용하는 방식으로 상수별로 다르게 동작하는 코드를 쉽게 구현할 수 있다.**
- 단순히 각 열거 타입 상수의 동작을 람다로 구현해 생성자에 넘기고, 생성자는 이 람다를 인스턴스 필드로 저장해둔다, 그 다음 apply 메서드에서 필드에 저장된 람다를 호출하기만 하면 된다. 이렇게 구현하면 전 버전보다 훨씬 깔끔해진다.

```java
public enum Operation {
	PLUS("+", (x,y) -> {
			// 참고 : 열거 타입 생성자 안의 람다는 열거 타입의 인스턴스 멤에 접근 할 수 없다.
			// 인스턴스는 런타임에 만들어지기 때문이다.
			x + y
	}),
	MINUS("-", (x,y) -> x - y),
	TIMES("*", (x,y) -> x * y),
	DIVIDE("/", (x,y) -> x / y);

	private final String symbol;
	private final DoubleBinaryOperator op;

	Operation(String symbol, DoubleBinaryOperator op) {
		this.symbol = symbol;
		this.op = op;
	}

	@Override
	public String toString() {
		return symbol;
	}

	public double apply(double x, double y) {
		return op.applyAsdouble(x,y)
	}
}
```

<aside>
🤔 이 코드에서 열거 타입 상수의 동작을 표현한 람다를 `DoubleBinaryOperator` 인터페이스 변수에 할당했다. 
`DoubleBinaryOperator`는 `java.util.function` 패키지가 제공하는 다양한 함수 인터페이스 중 하나로 `double` 타입 인수 2개를 받아 `double` 타입 결과를 돌려준다.

</aside>

## 🤔  람다식이 무조건 좋은 것일까?

- 람다 기반 Operation 열거 타입을 보면 추상메서드가 더이상 사용할 이유가 없다고 느낄지 모르지만, 꼭 그렇지는 않다.
- 메서드나 클래스와 달리 **람다는 이름이 없고 문서화도 못한다.** 따라서 코드 자체로 **동작이 명확하게 설명되지 않거나 코드 줄 수가 많아지면 람다를 쓰지 말아야 한다.**
- **람다는 한 줄 일때 가장 좋고** **길어야 세줄 안에 끝내는게 좋다.**
    - 람다가 길거나 읽기 어렵다면 더 간단히 줄여보거나 람다를 쓰지 않는쪽으로 리팩토링을 해야한다.

### 람다는 함수형 인터페이스에서만 사용할 수 있다.

- **하지만 람다로 대체할 수 없는 곳이 있다. 람다는 함수형 인터페이스 에서만 쓰인다.**
- 예컨대 추상 클래스의 인스턴스를 만들 때 람다를 쓸 수 없으니, 익명클래스를 써야 한다.

### 람다는 자신을 참조할 수 없다.

- 람다에서의 this 키워드는 바깥 인스턴스를 가리킨다. 반면 익명 클래스에서의 this는 익명 클래스의 인스턴스 자신을 가리킨다.
- 함수 객체가 자신을 참조해야하는 경우, 익명클래스를 사용해야 한다.

```java
public interface LambdaTest {
	int value(int i);
}

public class Test {
	private final int value = 100;

	public LambdaTest lambdaTest = new LambdaTest() {
		final int value = 200;
		@Override
		public int value(int i) {
			return i + this.value; // 익명 클래스는 자신을 가리킴
	}
};

	public LambdaTest lambdaTest2 = i -> i + this.value // 람다는 바깥 인스턴스를 가리킴
}
```

### 람다는 직렬화를 해서는 안된다.

- 람다도 익명 클래스처럼 직렬화 형태가 구현별로 다를 수 있다. 그렇기 때문에 직렬화하는 일을 극히 삼가해야 한다.(익명 클래스도 마찬가지)
- 직렬화해야만 하는 함수 객체가 있다면 private static 중첩 클래스의 인스턴스를 사용하도록 하자.

## 🧑🏻‍⚖️  결론

- 자바 8이 등장하면서 작은 함수 객체를 구현하는데 적합한 람다가 도입 되었다.
- 익명 클래스는 (함수형 인터페이스가 아닌) 타입의 인스턴스를 만들 때만 사용하라.
- 람다는 한 줄이 제일 좋으며 최대 세줄이다. 더 길어진다면 람다를 쓰지 않는 쪽으로 리팩토링하자 !