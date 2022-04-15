## Comparable 인터페이스의 유일무이한 메서드, compareTo

- `comapreTo` 값을 비교하는 method
    - `A.compareT(B)`
        - A < B : 음수(-1) 반환
        - A = B : 0 반환
        - A > B : 양수(+1) 반환
- compareTo는 Object의 메서드가 아닌, Comparable 인터페이스의 메서드

```java
public class ClassName implements Comparable { ... }
```

```java
public class Main {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        System.out.println(a.compareTo(b)); // -1
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        System.out.println(a.compareTo(b));
				// error: int cannot be dereferenced
    }
}
```

- equals 메서드처럼 값이 같은지 비교하는 기능 + 값의 대소비교 가능 제공
- 값의 순서를 매기는데 유용하게 사용된다 : 검색/정렬 메서드, 자동 정렬 Collection 등에서 사용됨

```java
public PriorityQueue(Collection<? extends E> c) {
    if (c instanceof SortedSet<?>) {
        SortedSet<? extends E> ss = (SortedSet<? extends E>) c;
        this.comparator = (Comparator<? super E>) ss.comparator();
        initElementsFromCollection(ss);
    }
    else if (c instanceof PriorityQueue<?>) {
        PriorityQueue<? extends E> pq = (PriorityQueue<? extends E>) c;
        this.comparator = (Comparator<? super E>) pq.comparator();
        initFromPriorityQueue(pq);
    }
    else {
        this.comparator = null;
        initFromCollection(c);
    }
}
```

- Generic Type 으로 다양한 객체 타입에서 사용할 수 있다.

```java
public class Main {
    public static void main(String[] args) {
        Integer iA = 1; Integer iB = 2;
        Float fA = 1F; Float fB = 2F;
        Double dA = 1.0; Double dB = 2.0;
        
        System.out.println(iA.compareTo(iB)); // -1
        System.out.println(fA.compareTo(fB)); // -1
        System.out.println(dA.compareTo(dB)); // -1
    }
}
```

- 즉, 직접 작성한 클래스에서 Comparable을 구현하면, 해당 인터페이스를 구현하는 다양한 제네릭
알고리즘과 Collection을 사용할 수 있다는 장점이 있다.
    - Java 라이브러리의 모든 값 클래스(Integer, Float, Double 등)와 열거 타입이 Comparable 구현
    
    ```java
    public final class Integer extends Number
            implements Comparable<Integer>, Constable, ConstantDesc { ... }
    ```
    
    ```java
    public abstract class Enum<E extends Enum<E>>
            implements Constable, Comparable<E>, Serializable { ... }
    ```
    
    - 알파벳, 숫자, 날짜 등 순서가 명확한 값 클래스를 작성할 때는 꼭 Comparable 인터페이스를 구현하자.
- 정렬된 컬렉션인 TreeSet, TreeMap 과 검색/정렬 기능을 제공하는 유틸리티 클래스 Collections, Arrays 역시 Comparable을 활용한다.<br><br>

## compareTo 메서드의 세가지 규약

💡 `A.compareT(B)`

- **A < B** : 음수 반환  ( `sgn(A.compareTo(B))` = -1 )
- **A = B** : 0 반환      ( `sgn(A.compareTo(B))` =  0 )
- **A > B** : 양수 반환  ( `sgn(A.compareTo(B))` = -1 )

- sgn(x.compareTo(y)) == -sgn(y.compareTo(x))
    - 모든 x, y에 대해 x > y 이면 y < x 이다.
    - 한쪽에서 예외가 발생하면 다른 쪽에서도 응당 예외가 발생해야 함
- x.compareTo(y) > 0 && y.compareTo(z) > 0 이면 x.compareTo(z)
    - 모든 x, y, z 에 대해 x > y 이고 y > z 이면 x > z 이다
- x.compareTo(y) == 0 이면 sgn(x.compareTo(z)) == sgn(y.compareTo(z))
    - x = y 이면, 모든 z에 대해 x > z , x < z 의 참/거짓 여부는 y > z , y < z 의 참/거짓 여부와 동일
- (권장) x.compareTo(y) == 0 이면 x.equals(y) == 0 이다.
    - Comparable을 구현하지만 위 사항을 지키지 않는 클래스는 반드시 그 사실을 명시해줘야 한다.
    - 주의! 이 클래스의 순서는 equals 메서드와 일관되지 않다.

- 특정한 오류를 발생시키는 것은 아니지만, 크고 작은 문제들이 발생할 수 있다.
- 정렬된 컬렉션들은 값이 같은지를 비교할 때 equals 대신 compareTo를 사용하기 때문

```java
public class Main {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = new BigDecimal("1.00");

        System.out.println(a.compareTo(b)); // 0
        System.out.println(a.equals(b)); // false

				HashSet<Object> A = new HashSet<>(); // 동치 비교에 equals 사용
        TreeSet<Object> B = new TreeSet<>(); // 동치 비교에 compareTo 사용

        A.add(a); A.add(b);
        B.add(a); B.add(b);

        System.out.println(A.size()); // 2
        System.out.println(B.size()); // 1
    }
}
```
<br><br>
## compareTo 메서드의 작성 요령

- 타입이 다른 객체를 신경쓰지 않아도 됨 : 대부분 ClassCastException을 던지는 식으로 구현한다.
    - 일반적으로 객체간의 비교는 객체들이 구현한 공통 인터페이스를 매개로 이루어지기 때문
- 기존 클래스를 확장한 구체 클래스에서 새로운 값 Component를 추가하면 compareTo 규약을 위반하는
상황이 발생할 수 있으니 주의하자.
    - 만약 새로운 값 Component를 추가하고 싶다면, 새로운 클래스를 만들어서 이 클래스에 원래 클래스의 인스턴스를 가리키는 필드를 두자. 그 다음 내부 인스턴스를 반환하는 메서드르 제공하면 된다.
    - 이렇게 하면 바깥 클래스에 우리가 원하는 compareTo 메서드를 구현해넣을 수 있으며
    필요에따라 바깥 클래스의 인스턴스를 원래 클래스의 인스턴스로 다룰수도 있다.
- Comparable은 제네릭 인터페이스 이므로 compareTo 메서드의 parameter 타입은 컴파일 타임에
정해진다. 즉 입력 인수의 타입을 확인하거나 형변환할 필요가 없음
    - parameter 타입이 잘못되었다면 컴파일 자체가 되지 않는다.
    - null 이 인수로 들어오면 NullPointerException이 Throw 되어야 한다.
- compareTo 메서드는 값이 동일한지를 비교하는데 보다는 값의 대소와 순서를 비교하는데 주로 사용된다.
    - 클래스에 핵심 필드가 여러개라면 가장 핵심적인 필드부터 비교해나가자.
    
    ```java
    public int compareTo(PhoneNumber pn) {
        int result = Short.compare(areaCode, pn.areaCode); // 가장 중요한 필드
    
        if (result == 0) {
            result = Short.compare(prefix, pn.prefix); // 두번째로 중요한 필드
    
    				// 세번째로 중요한 필드
            if (result == 0) result = Short.compare(lineNum, pn.lineNum);
        }
        return result;
    }
    ```
    
- 객체 참조 필드를 비교하려면 compareTo 메서드를 재귀적으로 호출하면 된다.<br><br>

## Java 제공 비교자의 활용

- Comparable을 구현하지 않은 필드나, 표준이 아닌 순서로 비교해야 한다면 Java에서 제공하는 비교자(Comparator)를 사용할 수 있다.
- 객체 참조 필드가 하나인 비교자 : `compare`<br><br>

## 관계연산자 >, < 사용에 주의하라

- compareTo 메서드에서 관계 연산자 >, <를 사용하는 방식은 거추장스럽고 오류를 유발하기 쉽다.
- Java 7 부터 라이브러리의 모든 값 클래스(Integer, Float, Double 등)가 Comparable을 구현하고
있으므로, 해당 기능을 사용하는것이 권장된다.<br><br>

## Java의 비교자 생성 메서드와 객체 참조용 비교자 생성 메서드

```java
private static final Comparator<PhoneNumber> COMPARATOR =
        Comparator.comparingInt((PhoneNumber pn) -> pn.areaCode)
                .thenComparingInt(pn -> pn.prefix)
                .thenComparingInt(pn -> pn.lineNum);

public int compareTo(PhoneNumber pn) {
    return COMPARATOR.compare(this, pn);
}
```

- `comparing` 객체 참조를 키에 맵핑하는 키 추출 함수를 인수로 받아,
그 키를 기준으로 순서를 정하는 비교자를 반환하는 정적 메서드
    - 입력 인수의 타입을 명시해줘야 한다. Java의 타입 추론 능력이 해당 타입을 인식한 만큼 강력하지 않으므로
- `thenComparing`  키 추출자 함수를 입력받아 다시 비교자를 반환하는 인스턴스 메서드
    - 원하는 만큼 연달아 호출할 수 있다.
    - 입력 인수의 타입을 명시해줄 필요가 없다. 자바의 타입 추론 능력이 이정도는 추론 가능하므로
<br><br>
