## Item29 : 이왕이면 제네릭 타입으로 만들라

## 제네릭 타입의 클래스를 생성하는 것

- Java JDK에서 제공되는 제네릭 타입의 클래스 메소드를 사용하는 것은 크게 어렵지 않다.
- 직접 제네릭 타입의 클래스를 선언하는 것은 어떨까?
- 이번 아이템에서 함께해줄 코드를 소개합니다! (아이템 7에서 다룬 Object 기반 스택 코드)

```java
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size);
    }
}
```

- 이 클래스는 클라이언트가 스택으로부터 객체를 꺼낸 후에 형변환을 해줘야한다.
    - 이는 런타임 오류를 유발할 수 있으므로 제네릭 타입으로 구현해주는 것이 안전하다.
    - 위 클래스를 제네릭 타입으로 바꿔도 해당 클래스를 사용하던 기존 객체들에게는 아무런 해가 없다.

<br>

## 일반 클래스를 제네릭 클래스로 만드는 법

    1. 클래스 선언에 타입 매개변수를 추가한다.

- 스택이 담을 원소의 타입을 하나 추가해줘야 한다.
    - 타입 이름으로는 보통 `E`를 사용한다.
- 그 다음 코드에 쓰인 `Object` 자료형을 적절한 타입 매개변수 자료형으로 바꿔주자.
    - `Object` → `E`

```java
public class Stack<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new E[DEFAULT_INITIAL_CAPACITY]; // 컴파일 에러
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0)
            throw new EmptyStackException();
        E result = elements[--size];
        elements[size] = null;
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size);
    }
}
```

- 이 단계에서 대체로 하나 이상의 오류나 경고가 뜨는데, 위 예시에서도 하나의 오류가 발생한다.
    - `E` 같이 실체화가 불가능한 타입으로는 배열을 만들 수 없기 때문
- 제네릭 배열 생성을 금지하는 제약을 우회하는 방법
    - Object 배열을 생성한 다음 제네릭 배열로 형변환해보면 컴파일러는 오류  대신 경고를 내보낸다.
        - 컴파일러는 이 프로그램의 타입 안전성 증명할 방법이 없지만 우리는 할 수 있다.
        - 비검사 형변환이 프로그램의 타입 안전성을 해치지 않음을 스스로 확인해야 한다.
        - 문제의 배열 elements는 `private` 필드에 저장되고, 클라이언트로 반환되거나 다른 메소드에 전달되는 일이 전혀 없다.
        - 즉 push 메소드를 통해 배열에 저장되는 원소의 타입은 항상 `E` 이므로 이 비검사 형변환은 확실히 안전하다.
    - 비검사 형변환이 안전함을 직접 증명했다면 `@SuppressWarnings` 어노테이션으로 해당 경고를 숨길 수 있다.
    - 해당 예시에서는 생성자가 비검사 배열 생성 말고는 하는 일이 없으므로 생성자 전체에서 경고를 숨겨도 된다.
    - 이렇게 어노테이션을 사용하면 Stack은 오류나 경고 없이 컴파일되고 명시적으로 형변환하지 않아도 ClassCastException을 걱정없이 사용할 수 있다.
    <br>
    
    ```java
    // 배열 elements는 push(E)로 넘어온 E 인스턴스만 담는다.
    // 타입 안정성을 보장하지만 이 배열의 런타임 타입은 E[]가 아닌 Object[] 이다.
    @SuppressWarnings("unckecked")
    public Stack() {
    	elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }
    ```
    
- elements 필드의 타입을 `E[]` 에서 `Objects[]`로 바꾸는 방법
    - 이렇게하면 배열이 반환한 원소를 E로 형변환했을 때 오류 대신 경고가 뜬다.
    - E는 실체화 불가 타입이므로 컴파일러는 런타임에 이뤄지는 형변환이 안전한지 증명할 방법이 없다.
    - 이번에도 개발자가 직접 증명하고 경고를 숨길 수 있다.
    - pop 메소드 전체에서 경고를 숨지기 말고 비검사 형변환을 수행하는 할당문에서만 숨길 수 있다.
    <br>
    
    ```java
    // 비검사 경고를 적절히 숨긴다.
    public E pop() {
    	if (size == 0) throw new EmptyStackException();
    	
    	// push에서 E 타입만 허용하므로 이 형변환은 안전하다.
    	@SuppressWarnings("unckecked") E result = (E)elements[--size];
    
    	elements[size] = null; // 다 쓴 참조 해제
    	return result;
    }
    ```
    
- 첫번째 방법
    - 가독성이 더 좋다.
        - 배열의 타임을 `E[]`로 선언하여 오직 `E` 타입 인스턴스만 받음을 확실히 어필할 수 있고 코드도 더 짧다.
        - 첫번째 방식에서는 형변환을 배열 생성 시 단 한번만 해주면 되지만, 두번째 방법에서는 배열에서 원소를 읽을 때마다 해줘야 한다.
        - 현업에서는 첫번째 방식이 더 많이 사용된다.
    - Heap Pollution(힙 오염)을 일으킨다.
        - 배열의 런타임 타입이 컴파일 타입과 다르기 때문
        - 힙 오염을 피하고자 두번째 방법이 채택되는 경우가 많다.

<br>

## 제네릭 타입과 배열 vs 리스트

- 지금까지 설명된 Stack 예시는 Item 28 : 배열보다는 리스트를 우선하라 와 모순되어 보일 수 있다.
- 제네릭 타입의 경우 리스트를 사용하는것이 항상 가능하지도 않고, 항상 더 좋다고 이야기 할 수도 없다.
    - Java가 리스트를 기본 타입으로 제공하지 않기 때문에 ArrayList 같은 제네릭 타입도 결국 기본 타입인 배열을 사용하여 구현해야 한다.
    - HashMap 같은 제네릭 타입은 성능을 높일 목적으로 배열을 사용하기도 한다.
- 대다수의 제네릭 타입은 타입 매개변수에 아무런 제약을 두지 않는다.
- Stack<Object>, Stack<int[]>, Stack<List<String>>, Stack 등 어떤 참조 타임으로도 Stack을 만들 수 있다.
- 단 기본 타입은 사용할 수 없다. Stack<int>, Stack<double>을 만들려고 하면 컴파일 오류가 난다.
- 자바 제네릭 타입 시스템의 근본적인 문제이지만, 박싱된 기본 타입을 사용해 우회할 수 있다.
- 타입 매개변수에 제약을 두는 제네릭 타입도 있다.
    - 예) `java.util.concurrent.DelayQueue`
        
        ```java
        class DelayQueue<E extends Delayed> implements BlockingQueue<E>
        ```
        
        - 타입 매개변수 목록인 <`E extends Delayed`>는 `java.util.concurrent.Delayed`의 하위 타입만 받는다는 뜻이다.
        - 이렇게 하여 `DelayQueue` 자신과 `DelayQueue`를 사용하는 클라이언트는 `DelayQueue`의 원소에서 형변환없이 곧바로 `Delyed` 클래스의 메소드를 호출할 수 있다.
        - `ClassCastException` 걱정은 할 필요가 없다.
- 이러한 타입 매개변수 E 를 한정적 타입 매개변수(bounded type parameter)라고 한다.
- 모든 타입은 자기 자신의 하위 타입이므로 `DelayQueue<Delayed>`로 사용할 수 있다.
