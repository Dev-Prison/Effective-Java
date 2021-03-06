## Item19 : 상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라.

    클래스의 상속을 허용하면 문제가 발생할 수 있다.

    기존 클래스(A) 내부에서 먼저 정의 및 호출되는 함수(F)가 재정의 가능한 함수일 때,
    다른 클래스(B)가 A 클래스를 상속한 다음 F 함수를 재정의(F’)하여 사용할 수 있는데
    모종의 이유로 클래스 A 에서 F 함수를 수정하면 클래스 B 의 F’ 함수에서 문제가 생길 수 있다.

    이러한 문제가 왜 발생하는지 좀 더 자세히 알아보고, 어떻게 방지할 수 있는지 알아보자.

## 주석을 잘 남겨라

- `외부`에서 `상속용 클래스`의 메서드를 재정의하면 어떤 일이 일어나는지 정확히 정리하여 문서로 남겨라
- `상속용 클래스`는 재정의 가능한 메서드들을 `내부`에서 어떻게 이용하는지 문서로 남겨야 한다.
- 클래스 내부에서 재정의 가능한 메서드를 호출하고 있다면, 그 사실과 어떤 순서로 호출하는지, 각각의 호출 결과가 이어지는 처리에 어떤 영향을 주는지도 문서로 남겨라
    - 재정의 가능 메서드 : `final`이 아닌 모든 `public`, `protected` 메서드
- 아니 그냥 재정의 가능 메서드를 호출할 수 있는 모든 상황을 문서로 남겨라
    - 백그라운드 스레드나 정적 초기화 과정에서도 호출이 일어날 수 있음
- 메서드에서 종종 `Implementation Requirements`로 시작하는 주석을 발견할 수 있는데,
이는 메서드의 내부 동작 방식에 대한 설명을 적어놓은 주석이다.
    - Command Line에서  `-tag “implSpec:a:Implementation Requirements:”`
    명령어를 입력해 `@implSpec` 어노테이션을 새롭게 지정할 수 있는데, 이 어노테이션을 주석에 붙이면
    `Implementation Requirements` 라는 문구가 자동으로 생성된다. (자바독의 커스텀 태그 기능)
    - 자바 개발팀에서 내부적으로 사용하는 규약
    - Annotation 이름을 반드시 `@implSpec`으로 해야할 필요는 없지만, 언젠가 표준 태그로 정의될지도 모르니 이왕이면 자바 개발팀과 같은 방식으로 사용하는 것을 추천한다. (by 옮긴이)

![aaa](https://user-images.githubusercontent.com/48689213/167621054-bac8e681-1380-4c4c-b40c-7caabe95d510.png)
(java.util.AbstractCollection의 remove 메서드)

- `@implSpec`은 이 클래스를 상속하여 메서드를 재정의했을 때 나타날 효과를 상세히 설명하고 있다.
    - `java.util.AbstractCollection`의 `remove` 메서드는 내부적으로 iterator의 remove 메서드를 사용하고 있다.
    - 위 설명에 의하면 `iterator` 인터페이스를 구현한 구현체에 `remove` 메소드가 구현되어 있지 않은
    경우 `UnsupportedOperationException`이 발생한다.

<img width="484" alt="t1" src="https://user-images.githubusercontent.com/48689213/167621226-dcd68bee-5b89-4fff-baf1-da03a14e7dc2.png">
<img width="581" alt="t2" src="https://user-images.githubusercontent.com/48689213/167621274-9adf6c38-95ee-4f1b-8001-3bb928012810.png">

- `iterator` 메서드를 재정의하면 `java.util.AbstractCollection`의 `remove` 메서드의 동작에 영향을 준다.
- iterator 메서드를 재정의하면 반드시 remove 메소드도 함께 구현해 주어야 `java.util.AbstractCollection`의 `remove` 메서드를 오류 없이 사용할 수 있다.
- 이처럼 클래스를 안전하게 상속할 수 있도록 하려면 클래스의 내부 구현 방식을 설명으로 남겨야 한다.
    - `@implSpec` 어노테이션을 적극 활용하라

<br><br>

## 클래스의 Hook 을 잘 선별하여 protected 메서드 형태로 공개하라

- 효율적인 하위 클래스를 큰 어려움 없이 만들 수 있게 하려면
내부 동작 과정에 끼어들 수 있는 훅(hook)을 protected 메서드(재정의 가능한)로 제공하는것이 좋다.

<img width="661" alt="k3" src="https://user-images.githubusercontent.com/48689213/167621400-96c27a41-1e37-4f0e-9fdb-c4acff76c704.png">

- `removeRange` 메서드는 특정 리스트 또는 부분 리스트의 `clear` 메서드에서 사용된다고 나와있다.
- 또한 해당 메서드를 재정의하면 특정 리스트 또는 부분 리스트의 `clear` 메서드를 고성능으로 만들 수 있다고 명시되어있다.
- `removeRange` 메서드를 `protected` 접근 제어자로 제공한 이유는 단지 하위 클래스의 clear 메서드를 고성능으로 만들기 쉽게 하기 위해서이다.

- 상속용 클래스를 설계할 때 어떤 메서드를 `protected`로 노출해야 할지는 어떻게 결정할까?
    - 심사숙고해서 잘 예측해본 다음 실제 하위 클래스를 만들어 실험해보는것이 최선
        - 상속용 클래스를 시험하는 방법은 직접 하위 클래스를 만들어보는것이 **유일한** 방법이다.
        - 한 3개 정도 만들어 보는 것이 적당하다. (이 중 하나 이상은 제 3자가 작성해볼 것)
        - 상속용으로 설계한 클래스는 배포 전에 반드시 하위 클래스를 만들어 검증해야 한다.
- protected 메서드 하나하나가 내부 구현에 해당하므로 그 수는 가능한 한 적어야 하나
너무 적게 노출해서 상속으로 얻는 이점마저 없애지 않도록 주의해야 한다.
- 클래스를 확장해야 할 명확한 이유가 떠오르지 않는다면 상속을 금지하는 편이 낫다.

<br>

## 상속을 허용하는 클래스가 지켜야 할 제약

> 상속을 허용하는 클래스의 생성자는 클래스 내부의 재정의 가능 메서드를 호출하면 안된다.

- 프로그램 오작동의 원인이 됨
- 상위 클래스의 생성자가 하위 클래스의 생성자보다 먼저 호출되기 때문
- 하위 클래스에서 재정의된 메서드가 하위 클래스의 생성자보다 먼저 호출된다.
- 재정의된 메서드가 하위 클래스의 생성자에서 초기화되는 값에 의존한다면 의도대로 동작하지 않을 것

```java
public class Super {
    public Super() { overrideMe(); }
    public void overrideMe() { ... }
}
```

```java
public final class Sub extends Super {
    private final Instant instant;

    Sub() { instant = Instant.now(); }

    @Override
		public void overrideMe() { System.out.println(instant); } 
}

Sub sub = new Sub(); // null 출력
sub.overrideMe(); // instant 출력
```

- 상위 클래스의 생성자는 하위 클래스의 생성자가 인스턴스 필드를 초기화 하기도 전에 `overrideMe` 함수를 호출하기 때문에 이러한 문제가 발생한다.
- `overrideMe` 메서드에서 `instant` 객체의 메서드를 호출하려 한다면 상위 클래스의 생성자가 `overrideMe` 메서드를 호출할 때 `NullPointerException`을 던지게 될 것이다.
- 위 코드에서는 `println`이 null 입력을 처리해주기 때문에 예외는 발생하지 않음

<br>

## 상속용 클래스의 설계와 Cloneable/Serializable 인터페이스

> 둘 중 하나라도 구현한 클래스를 상속할 수 있게 설계하는 것은 일반적으로 좋지 않은 생각이다.

- `clone` 과 `readObject` 메서드는 생성자와 비슷한 효과를 내기 때문 : 새로운 객체를 생성한다.
- 즉 상속용 클래스에서 Cloneable 또는 Serializable을 구현하게 된다면 이들을 구현할 때 따르는 제약도 생성자와 비슷하다.
- `clone` 과 `readObject` 메서드 모두 직/간접적으로 재정의 가능한 메서드를 호출해서는 안된다.

<br>

## 상속용으로 설계되지 않은 일반적인 구체 클래스

> 상속용으로 설계하지 않은 클래스는 상속을 금지하라

- 상속용으로 설계되지 않은 일반적인 구체 클래스는 final도 아니고 상속용으로 설계/문서화되지 않았다.
    - 그대로 두면 위험하다. 누가 상속해버릴지 몰라...
        - 구체 클래스의 내부를 수정했을 때 이를 확장한 외부 클래스에서 문제가 발생할 수 있다.
- 클래스의 상속을 금지시키는 두가지 방법
    - 클래스를 final로 선언하는 방법
    - 모든 생성자를 private 또는 package-private으로 선언하고 public 정적 팩터리를 만들어주는 방법
        - 클래스 내부에서 다양한 하위 클래스를 만들어 쓰는 등 유연한 작업을 가능하게 해준다.
- 상속을 금지해도 괜찮은 것인가?
    - 인터페이스가 있고, 클래스가 인터페이스를 구현했다면 상속을 금지해도 개발하는데 문제가 없다.
        - 예) Set / List / Map
    - 단 구체 클래스가 인터페이스를 구현하지 않았는데 상속을 금지해 버리면 상당히 불편해질 수 있다.
    - 이런 경우에는 클래스 내부에서 재정의 가능 메서드를 사용하지 않게 만들고, 해당 사실을 문서로 남기자.
        - 메서드를 재정의해도 다른 메서드의 동작에 영향을 주지 않게 만들어라.

<br>

## 클래스 동작을 유지하면서, 재정의 가능 메서드를 사용한 부분을 제거하는 법

```java
public class Super {
    public Super() { overrideMe(); }
    public void overrideMe() { ~ }
}
```

```java
public class Super {
    public Super() { overrideMe(); }
    public void overrideMe() { ~ }
    
    private void helperMethod() { ~ }
}
```

```java
public class Super {
    public Super() { helperMethod(); }
    public void overrideMe() { helperMethod(); }
    
    private void helperMethod() { ~ }
}
```

- 새로운 private 도우미 메서드를 생성한다.
- 각각의 재정의 가능 메서드 내용을 private 도우미 메서드로 옮긴다.
- 기존 재정의 가능 메서드가 있던 자리에서, 이 도우미 메서드를 호출하도록 수정한다.
- 기존 재정의 가능 메서드를 호출하는 다른 코드 역시 이 도우미 메서드를 호출하도록 수정한다.
