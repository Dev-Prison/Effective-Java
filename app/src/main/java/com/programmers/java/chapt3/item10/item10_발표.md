## (참고) Java 에서 동일성(identity) , 논리적 동치성== 동등성(equality)

```java
String str = "abc"; 
String str1 = new String("abc");
str == str1; // false -> 동일 비교
str.equals(str1); // true -> 동등 비교
```

- 동일성 비교 → == 연산자  ⇒ 참조값 비교 (물리적 동일함)
    - 언제 True? 사실상 하나의 객체만 메모리에 존재하는 경우
- 동등 비교 → 논리적 동치성(logical equality) 비교 → equals
    - 언제 True?
        - 동일한 경우
        - 서로다른 객체가 메모리 상에 존재하더라도, 동등성 기준에 따라 두 객체가 동등 하다고 판단되는 경우

---

## (서론) 일반규약을 지켜 오버라이딩 해야하는 이유 ??

- Object 클래스에서 final 이 아닌 메서드 (equlas, hashCode, toString, clone, finalize) 는 모두 **오버라이딩을 염두에 두고 설계된 것**들이며, **오버라이딩에 관한 일반규약이 정의되어있다**.
- 일반규약 → 규약을 지키지 않았다면, **일반규약을 준수한다고 가정하는 곳에서 오동작이 일어날 수** 있다.
    - 어떤 이도 섬이 아니다 ( John Donne) → 세상에 홀로 존재하는 객체는 없다 →  객체가 전달되는 경우도 존재한다 → **객체를 전달 받은 곳에서는 전달되어온 객체가 equals 규약을 지킨다고 가정하고 동작**한다.
        - ex) Collection class들
    - 원인 파악도 어렵다.

---

### (참고) Object 클래스의 equals() 메소드

Object class 의 equals() 는 비교연산자(==) 와 동일하다 → 즉, 참조값만을 비교한다.

> Java 에서는 “== “ 연산자가 있는데, 굳이 왜 equals() 메소드를 둔 걸까요?
>
> - Java 에서는 연산자에 대한 오버라이딩이 불가능하기 때문

그렇다면 String class 의 equals() 는 어떻게 내부 값을 비교한걸까요? → String 에서는 **이미 오버라이딩을 하고 있기 때문**

- Object 하위 클래스들에서는, equals() 메소드를 재정의하여 “원하는 논리적 동치성 비교를 수행" 해야 합니다.

---

## equals 재정의.. 꼭 해야할까??

잘못 재정의 하면 오히려 엄청난 버그를 초래하게 되기에 ***아예 재정의 하지 않는게 더 좋을 때도 있다.***

### equals 를 재정의 하지 않아도 되는 경우

- 각 인스턴스가 본질적으로 고유 : Thread 처럼 , ***“값을 표현" 하는게 아닌,*** 동작하는 개체를 표현하는 클래스
- 인스턴스의 “논리적 동치성"을 검사할 일 없는 경우 : 설계 할 때 부터  이러한 것이 필요하지 않다고 판단 되었을 수도 있다.
- 상위클래스에서 오버라이딩한 equals()가 하위 클래스에도 딱 들어맞는 경우
    - ex) HashSet 은 AbstractSet 의 equals() 를 사용
    - Set, List, Map 의 대부분 구현체들은 Abstract Set,List,Map 에 정의된 것을 상속받아 그대로 쓴다.
- 클래스가 private, package-private  && equals 메소드 호출 할 일이 없는 경우.

---

## equals 재정의가 필요한 경우는 언제일까?🐧

- ***값 클래스 ( 값을 표현하는 클래스 )***
    - **예외 : 같은 값의 인스턴스가 2개 이상 만들어지지 않음이 보장되는 “인스턴스 통제 클래스”** → Object 의 equals()로 동일성,동등성 모두 확인 ( 그 값을 가진 해당 클래스의 인스턴스는 항상 같은 인스턴스임이 보장 되기 때문 )
        - ex) enum
  
ex) *2차원  좌표평면 상의 위치를 나타내는 Point 클래스가 있다고 하자. 그리고 나와 친구는 누가 구슬을 더 멀리 굴렸는지 대결을 하고 있다*.

```java
    public class Point{
    	private final int x;
    	private final int y; 
    	public Point(int x, int y){
    		this.x = x;
    		this.y = y;
    	}
    	..
    }
    // 내가 던진 구슬이 굴러서 도착한 지점
    Point p1 = new Point(1,2);
    // 내 친구가 던진 구슬이 굴러서 도착한 지점
    Point p2 = new Point(1,2);
```

  p1 과 p2 가 같은 객체인지를 아는 것 보다는

  p1 과 p2 가 가진 좌표들의 값이 같은지 알고 싶을 것이다.


---

# equals 메소드 재정의 시에 따라야 하는 일반규약

> 수학적 개념을 떠올려보자
>

모든 조건에서 x 는 null 이 아님을 가정하고 있다.

1. 반사성 ( reflexibity) : null 이 아닌 모든 참조값 x 에 대해

```java
x.equals(x) = true  // 자기 자신에 대해 true 
```

1. 대칭성 ( symmetry) : null 이 아닌 모든 참조 값 x, y 에 대해

```java
if x.equals(y) is true
		y.equals(x)=true  // 반대로 한 경우에도 true 
```

1. 추이성 ( transitivity) : null 이 아닌 모든 참조값 x,y,z 에 대해

```java
if x.equals(y) is true && y.equals(z) is true
		x.equals(z) = true //  x = y 이고 y = z  => x = z 이다
```

1. 일관성( consistency) : null 이 아닌 모든 참조값 x,y 에 대해, x.equals(y) 를 반복해서 호출하는 경우 항상 true 이거나 항상 false 다 ( 일관된 결과)
2. non-null : null 이 아닌 모든 참조값 x 에 대해,

```java
x.equals(null) = false
```

---

반사성에 대해서는 다들 쉽게 이해하실 수 있을 것 같다.

그런데 *“대치성"이 만족되지 않는 경우는 어떤 경우*가 있을까?

## 대치성 : x.equals(y) = y.equals(x)

“AbcD”  와 “abcd” 를 동등한 문자열로 보는 클래스 CaseInsensitiveString 가 존재한다고 하자

```java
public final class CaseInsensitiveString {
	private final String s;

	public CaseInsensitiveString(String s) {
		this.s = Objects.requireNonNull(s);
	}

	// Broken - violates symmetry!
	@Override public boolean equals(Object o) {
		if (o instanceof CaseInsensitiveString)
			return s.equalsIgnoreCase( // String 클래스 메소드 - 대소문자를 무시하고 비교한다.
				((CaseInsensitiveString) o).s);
		if (o instanceof String)  // One-way interoperability!
			return s.equalsIgnoreCase((String) o);
		return false;
	}
```

💥 이 경우 **대치성을 위반**하였다.

```java
	@Test
	public void givenCaseInsensitiveStringAndString_WhenEvaluatingEquality_ThenFailSymmetry() {
		CaseInsensitiveString insensitiveString = new CaseInsensitiveString("AbcD");
		String string = "abcd";

		assertThat(string.equals(insensitiveString) == insensitiveString.equals(string),
			is(Boolean.FALSE));
	}
```

Collection 에 넣어본다면 어떤일이 일어날까요?

```java
    @Test
	public void givenCaseInsensitiveStringAndString_WhenAddInsensitiveStringToListThenCheckByString_ThenFalse(){
		// given 
		CaseInsensitiveString insensitiveString = new CaseInsensitiveString("AbcD");
		String string = "abcd";
		// when 
		List<CaseInsensitiveString> list = new ArrayList<>();
		list.add(insensitiveString);
		// then 
		assertThat(list.contains(string), is(Boolean.FALSE));
	}
```

이 결과는 JDK 마다 다를 수 있다고 한다. 현재 사용하고 있는 JDK 의 경우에는 **내부적으로**

- ***o 의 equals() 를 호출***

    ```java
    int indexOfRange(Object o, int start, int end) {
            Object[] es = elementData;
            if (o == null) {
                for (int i = start; i < end; i++) {
                    if (es[i] == null) {
                        return i;
                    }
                }
            } else {
                for (int i = start; i < end; i++) {
                    if (o.equals(es[i])) {
                        return i;
                    }
                }
            }
            return -1;
        }
    ```


> 즉 , equals 규약을 어긴다면, **그 객체를 “사용하는 다른 객체" 들에서 어떻게 반응 할 지** 결과를 알 수 없다.
>

이런 문제가 발생한 것은, *`CaseInsensitiveString` 의 equals 를 String 과도 연동하는 욕심에서 비롯되었다고도 볼 수 있다.*.

```java
    @Override
	public boolean equals(Object o) {
		return o instanceof CaseInsensitiveStringRefactored &&
			((CaseInsensitiveStringRefactored)o).s.equalsIgnoreCase(s);
	}
```

따라서 이를 변경하고나면 “대치성 위반” 도 해결된다.

```java
    @Test
	public void given_CaseInsensitiveStringRefactoredAndString_WhenEqualityEvaluatedBetweenThen_ThenSuccessSymmetry(){
		CaseInsensitiveStringRefactored insensitiveString = new CaseInsensitiveStringRefactored("AbcD");
		String string = "abcd";

		assertThat(string.equals(insensitiveString) == insensitiveString.equals(string),
			is(Boolean.TRUE));
	}
```

---

## 추이성 : x.equals(y)이고 y.equals(z) 이면 x.equals(z) 이다.

> 문제상황 : ***상위클래스에 없는 새로운 필드를 하위클래스에 추가***하는 경우
>

![image](https://user-images.githubusercontent.com/53856184/162424472-0c837a7a-5525-4934-aba3-fdc0387dd559.png)
- 💥 **대치성 위반**

```java
    @Test
	public void given_ColorPointAndPoint_when_EqualityEvaluated_then_symmetryFails() {
		ColorPoint cp = new ColorPoint(1, 2, new Color(100));
		Point p = new Point(1, 2);

		assertThat(cp.equals(p) == p.equals(cp), is(Boolean.FALSE));
	}

	@Test
	public void given_ColorPointAndPoint_when_EqualityEvaluatedByUsingEqualsOfColorPoint_then_Fails() {
		ColorPoint cp = new ColorPoint(1, 2, new Color(100));
		Point p = new Point(1, 2);

		assertThat(cp.equals(p), is(Boolean.FALSE));
	}
```

cp.equals(p) 도 true 가 나오게 만들어야할까요???

Point 타입 객체에 대한 equals 의 결과에 대해, ***하위클래스에 추가적으로 가진 필드가 영향을 끼치지 못하도록*** 만들어 보자

```java
    @Override
	public boolean equals(Object o) {
		if(!(o instanceof Point) )return false;
		
		if(!(o instanceof ColorPointRefactoredOne)) return o.equals(this); // 💥 o 인스턴스의 equals() 를 사용하면 괜찮겠지?????
		return super.equals((ColorPointRefactoredOne)o) && ((ColorPointRefactoredOne)o).color.equals(color); //
	}
```

- 💥 대칭성은 해결되지만, **추이성이 위반**됩니다.

```java
    @Test
	public void given_ColorPointRefactoredAndPoint_when_EqualityEvaluated_then_SymmetrySuccess(){
		Point p = new Point(1,2);
		ColorPointRefactoredOne cp = new ColorPointRefactoredOne(1,2,new Color(100));

		assertThat(cp.equals(p) == p.equals(cp), is(Boolean.TRUE));
	}
```

```java
    @Test
	public void given_ThreePointType_when_EqualityEvaluated_thenFailTransitivity(){
		Point p = new Point(1,2);
		ColorPointRefactoredOne cp1 = new ColorPointRefactoredOne(1,2,new Color(100));
		ColorPointRefactoredOne cp2 = new ColorPointRefactoredOne(1,2,new Color(120));
		
		assertThat(p.equals(cp1), is(Boolean.TRUE));
		assertThat(p.equals(cp2), is(Boolean.TRUE));
		assertThat(cp1.equals(cp2), is(Boolean.FALSE)); // !! 
	}
```

- 💥 Point 를 상속하며, 동일한 방식으로 equals() 를 오버라이딩한 클래스가 존재하는 경우 → **무한 재귀 발생**

```java
    @Test
	public void given_TwoPointSubclassTypedInstance_when_EqualityEvaluated_thenStackOverFlowBecauseOfRecursion(){
		ColorPointRefactoredOne cp1 = new ColorPointRefactoredOne(1,2,new Color(100));
		ColorPointRefactoredAnotherOne cp2 = new ColorPointRefactoredAnotherOne(1,2,new Color(100));

		assertThrows(StackOverflowError.class, ()-> cp1.equals(cp2));
	}
```

> **객체 지향적 추상화의 이점을 포기하지 않는다면**
>
>
> 구체 클래스를 **“확장"해 “새로운 값을 추가"하면서 equals 규약을 만족시킬 방법은 존재하지 않는**다.
>

여기서 말하는 **“객체 지향적 추상화" 란 LSP 원칙을 따르는 구현**을 말한다.

> *LSP 에 따르면 Point 의 하위 클래스(ColorPoint)는 정의상 여전히 Point 이므로 어디서든 Point 로서 활용될 수 있어야 한다*
>

위의 equals() 메소드들 은 LSP 를 지키기 위해 instanceof 를 사용해 왔다.

------
### (참고) instanceof 와 getClass()

- instanceof : 좌측의 참조객체가, 우측 타입과 “같거나, ***하위타입" 의 인스턴스인지 확인***
- getClass() : 두 타입이 완전하게 동일한 타입인지 체크할 때 사용하게 된다.
---
### LSP(리스코프 치환원칙) 을 따르지 않는다면?

만약 instanceof 가 아닌 getClass() 를 사용한다면, 각 클래스들은 **자신과 완전히 동일한 타입의 인스턴스가 아닌한 무조건 false 를 리턴**하게 되니 **동치성, 추이성을 위반하지는 않을 것**이다.

```java
    @Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != getClass())
			return false;
		Point p = (Point)o;
		return p.x == x && p.y == y;
	}
```

하지만 LSP 를 위반하는 것 자체는 문제가 있다.

- ColorPoint 는 정의상 여전히 Point 이므로, 어디서든 Point로서 활용될 수 있어야하는데 이것이 불가능해진다.

예를들어, *현재 좌표평면위에서 이미 칠해진 칸에는 다른 점을 칠할 수 없는 것이 원칙이라 하자. 그런데 리스코프 원칙을 따르지 않는  Point의 경우, 이미 Point가 위치하는 칸에 또 ColorPoint 를 칠하는 것이 가능하다*

```java
	private <T> boolean isPossibleToFill(Set<T> set,T obj){
		if(set.contains(obj))return false;
		set.add(obj);
		return true;
	}

	@Test
	@DisplayName("이미 칠해져 있는 칸에 칠하려 하는 경우에도 칠해버린다")
	public void given_CoordinateFilledWithPointsAndColorPoints_when_TryingAddingPointToPositionAlreadyFilled_thenSuccess(){
		Set<PointAvoidLSP> coord = new HashSet<>();
		coord.add(new PointAvoidLSP(1,2));
		coord.add(new PointAvoidLSP(1,3));

		assertThat(isPossibleToFill(coord, new ColorPointAvoidLSP(1,2, new Color(100)))
                        , is(Boolean.TRUE));
	}
```

- Set 과 같이, 대부분 Collection 에서는 이런 작업에 equals() 메소드를 이용하기 때문

### 구체 클래스의 하위 클래스에 값을 추가할 방법은 없다

대신 이를 우회하기 위해 “상속 대신 Composition” 을 사용할 수 있다

```java
public class ColorPoint extends Point{
	private final Color color; 
 ...

```

- ColorPoint 를 이루는 각 Composition 들의 클래스에 정의된 equals 를 사용할 수 있어진다.

```java
public class ColorPoint {
    private final Point point;
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        point = new Point(x, y);
        this.color = Objects.requireNonNull(color);
    }

    // view method 
    public Point asPoint() {
        return point;
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof ColorPoint))
            return false;
        ColorPoint cp = (ColorPoint) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }

    @Override public int hashCode() {
        return 31 * point.hashCode() + color.hashCode();
    }
}
```
---
### 자바 라이브러리 중 구체 클래스를 확장해 값을 추가한 클래스에 유의

java.sql.Timestamp 는 [java.util.Data](http://java.util.Data) 를 확장 후 nanoseconds필드를 추가했다.

둘을 섞어서 사용할 경우 문제가 생길 위험이 크기에 주의하자.

------
### 추상클래스의 하위클래스는 equals 규약을 지키며 값 추가 가능

> 상위 클래스를 직접 인스턴스로 만드는 것이 불가능하다면, 위의 문제들이 일어나지 않기 때문
>
---

## 일관성 : 한 번 true 면 영원히 true

> 클래스가 불변이든 가변이든, **equals의 판단에 “신뢰 할 수 없는 자원" 이 끼어들게 해서는 안 된다**.
>

따라서, 항상 메모리에 존재하는 객체만을 사용한 결정적(deterministc) 계산만 수행 해야 한다.

---
## non-null : x.equals(null) ⇒  false

> 모든 객체는 null 과 동등하지 않아야 한다 → x.equals(null) = false
>

생각해보면 많은 클래스들의 equals 구현부에는 항상 아래와 같이 명시적으로 null 을 검사하는 것을 보았다.

```java
    @Override
	public boolean equals(Object o) {
		if (o == null) return false;
```

- 그런데 위와 같이 명시적으로 null을 검사 할 필요가 없다
- equals 의 입력타입은 항상 Object 여야 하는데 (이는 뒤에서 다룸) , 논리적 동치성을 판단하기 위해, 이에 대한 타입 캐스트를 한다. 이 때 타입 검증을 하는 과정에서 instanceof 를 사용하는 것을 이미 보았다.

  > **instanceof 는 첫 번째 피연산자가 null 이면 false** 를 반환한다(JLS, 15.20.2)

```java
@Override
public boolean equals(Object o){
	if(!(o instanceof MyType)) return false;
	MyType mt = (MyType) o;
	..
}
```
---
# equals() 구현 방법 단계적 정리

> 다섯가지 규칙을 지켜가며, 핵심 필드들을 빠트리지 않고 구현해 나가야 한다.
>
1. == 연산자를 사용해 입력이 자기 자신의 참조와 같은지 확인
    - 단순한 성능 최적화용 코드
2. instanceof 연산자로 올바른 타입인지 확인
    - 올바른 타입 ?
        - ex) “인터페이스를 구현한 클래스"의 경우에는  이 클래스가 구현한 인터페이스 타입인지 확인
3. 입력을 올바른 타입으로 형변환
    - 이미 2번에서 타입 확인 했기에 3은 백프로 성공
4. 입력 객체와, 자기 자신의 대응되는 핵심 필드들이 모두 일치하는지 하나씩 검사
    - 논리적 상태와 관련 없는 필드는 비교하면 안 된다.
    - 2에서 “인터페이스" 를 사용했다면 , 필드값을 가져올 때도 “그 인터페이스의 메소드"를 사용해야함.
    - float, double → Float.compare(float,float), Double.compare(double,doulbe) 로 비교
        - Float.equlas, Double.equals() 는, 오토박싱을 수반할 수 있어 성능상 좋지 않음.
    - float,double 외의 Primitive type → ==연산자로 비교
    - 참조타입 필드 → 각각의 equals 메소드
    - 배열 → 모든 원소가 핵심필드라면 Arrays.equlas() 사용
    - null 도 정상 값으로 취급하는 필드인 경우 → Objects.equals(Object, Object)
        - 결과적으로, a,b 둘다 null 인 경우나 둘 중 하나가 null 이라고 하더라도 NPE 가 발생하지 않도록 해 줄 수가 있음.

        ```java
        public static boolean equals(Object a, Object b) {
                return (a == b) || (a != null && a.equals(b));
            }
        ```

    - 핵심 필드로부터 계산가능 파생필드는 굳이 비교할 필요는 없다.
        - 간혹, 파생필드 비교가 더 빠를 때도 있다. : 파생필드가 “객체 전체의 상태를 대표" 하는 경우.
- 비교하기 복잡한 필드를 가진 경우 → 그 필드의 표준형(canonical form)을 저장해 둔 후, 표준형끼리 비교.
    - 특히 불가변일 때 제격 ( 가변이면, 바뀔 때 마다 최신의 표준형으로 갱신해줘야해..)
- 어떤 필드를 먼저 비교하느냐에 따라 equals 성능이 달라질 수도 있다
    - 가능성이 더 크거나, 비교비용이 더 싼 필드를 먼저 비교하면 좋을 것.
- equals 구현후 → 대칭적?추이성이 있나? 일관적?? 확인 → AutoValue 를 통한 단위테스트를 해 봐야 한다. ( 오픈소스 프레임워크임. 클래스 어노테이션 하나만 추가하면 된다는 장점 )
5. equals 재정의시에는 hashCode 도 반드시 재정의 하자
6. 입력 타입은 반드시 Object 여야 한다.

-----
## 결론
- 물리적으로 동일한 것을 위해서는 Java의 경우 ==을 사용할 수 있고, 논리적 동치성(동등성) 을 비교하기 위해 equals를 사용하곤 한다.
- equals 일반 규약( 다섯가지 )을 지키지 않을 경우, equals를 오버라이딩한 어떤 객체(A)를 사용하는 측에서는, 해당 객체(A)가 equals 일반규약을 지키고 있는 것으로 가정하고 있기 때문에, 오동작이 일어날 수 있다.
- 이러한 오동작은 원인을 찾기도 어렵다.
- 객체의 핵심 필드들을 사용하여 일반규약을 지켜가며 equals 를 재정의 하도록 하자
- 요즘은 IDE 나 Lombok 등에서 자동으로 만들어주는 것을 사용하는 것이 내가 직접 만들며 규약을 지키지 못하는 오류를 범하는 것보다 더 안전한 것 같다.