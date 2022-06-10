## 6장 들어가기 : 열거 타입과 Annotation

- 자바에는 특수한 목적의 참조 타입이 두가지 있다.
    - 클래스의 일종인 `열거 타입` ( `Enum` )
    - 인터페이스의 일종인 `Annotation`
- 6장에서는 이 타입들을 올바르게 사용하는 방법을 알아보자!

## 정수 열거 패턴 (int enum pattern)

> 열거 타입이란?

- 일정 개수의 `상수 값`을 정의한 다음 그 외의 값은 허용하지 않는 타입
- 열거 타입으로 만들기 좋은 개념들
    - 사계절 (봄-여름-가을-겨울)
    - 요일 (월-화-수-목-금-토-일)
    - 카드게임의 카드 종류

> 열거 타입이 등장하기 전, 정수 열거 패턴

- 자바에서 열거 타입을 지원하기 전에는 아래처럼 정수 상수를 한 묶음 선언해서 사용하곤 했다.
    
    ```java
    public static final int MINT_FLAVOR = 1;
    public static final int MINT_MILK = 2;
    
    public static final int CHOCOLATE_FLAVOR = 1;
    public static final int CHOCOLATE_MILK = 2;
    ```
    
- 민트용 상수의 이름은 모두 `MINT_` 로 시작하고 초콜릿용 상수는 모두 `CHOCOLATE_` 으로 시작한다.
- `MINT` 도 `FLAVOR`와 `MILK` 를 상수 이름으로 사용하고 싶고, `CHCOLATE` 도 `FLAVOR`와 `MILK` 를 상수 이름으로 사용하고 싶은 경우 위 처럼 접두어를 이용해 충돌을 방지해줘야 한다.

> 정수 열거 패턴의 단점

- 타입 안전을 보장할 방법이 없으며 표현력도 좋지 않다.
    - 예) 초콜렛을 사용해야 할 상황에서 실수로 민트를 사용해도 컴파일러는 아무런 경고를 출력하지 않는다.
    
    ```java
    int doubleChocolate = CHOCOLATE_FLAVOR + MINT_FLAVOR
    ```
    
- 정수 열거 패턴을 사용한 프로그램은 깨지기 쉽다.
    - 평범한 상수를 나열한 것 뿐이라 컴파일하면 그 값이 클래스 파일에 그대로 새겨진다.
    - 즉 상수의 값이 변경되면 다시 컴파일해줘야 한다. 그렇지 않으면 프로그램이 엉뚱한 값으로 실행됨
- 값을 출력하거나 로그를 찍을 때 변수명은 확인할 수 없으니, 단지 숫자로만 출력되는 값의 의미를 파악하기 어렵다.
- 같은 `정수 열거 패턴` 그룹에 총 몇개의 상수가 있는지 알기 위해선 일일이 `COUNT` 해줘야 한다.

> 문자열 열거 패턴 (String Enum Pattern)

- 정수 대신 문자열 상수를 사용하는 변형 패턴이다.

  <img width="308" alt="sd" src="https://user-images.githubusercontent.com/48689213/172990246-f5490143-6f0d-49f7-8b67-35d074db00c7.png">
    
- 상수의 의미를 출력할 수 있다는 점은 좋지만, 문자열 상수의 이름 대신 문자열 값을 그대로 하드코딩하게 만들기 때문에 좋은 방법이 아니다.
- 하드코딩한 문자열에 오타가 있어도 컴파일러는 확인할 길이 없으니 런타임 버그가 생긴다.
- 문자열 비교에 따른 성능 저하가 발생한다.

## Java 열거 타입(Enum)의 등장

> Java 열거 타입

- 자바는 정수/문자열 열거 패턴의 단점을 말끔히 해결해주는 대안으로 열거 타입을 제시했다.
    
    ```java
    public enum Mint { FLAVOR, MILK }
    public enum Chocolate { FLAVOR, MILK }
    ```
    
- 자바의 열거 타입은 완전한 형태의 클래스이기 때문에 단순히 정수값이기만 한 다른 언어의 열거 타입보다 훨씬 강력하다.

> Java 열거 타입 구현 원리

- 열거 타입 자체는 클래스이다.
- 상수 하나당 자신의 인스턴스를 하나씩 만들어 public static final 필드로 공개한다.
- 밖에서 접근할 수 있는 생성자를 제공하지 않으므로 사실상 final 이다.
- 외부에서 열거 타입 인스턴스를 직접 생성하거나 확장할 수 없으므로, 만들어진 인스턴스는 단 하나만 존재함이 보장된다(싱글톤).

> 열거 타입의 장점

- 열거 타입은 컴파일타임 타입 안전성을 제공한다.

    <img width="368" alt="Untitled" src="https://user-images.githubusercontent.com/48689213/172990302-3760092a-5b5d-4f31-9c77-b6188d34906b.png">
    
    - `mintTest` 메소드의 인수는 `Mint`의 두가지 값중 하나임이 확실하며, 다른 타입의 값을 넘기려 하면 컴파일 오류가 난다.
- 열거 타입에는 각자의 이름공간이 있어서 이름이 같은 상수도 공존할 수 있다.

    <img width="471" alt="Untitled (1)" src="https://user-images.githubusercontent.com/48689213/172990340-bbb5faa0-5de3-4b87-af95-c8636d444fb6.png">

- 새로운 상수를 추가하거나 순서를 바꿔도 다시 컴파일 할 필요가 없다.
    - 공개되는 것은 오직 필드의 이름 뿐이라 상수 값이 클라이언트로 컴파일되어 각인되지 않기 때문
- `.toString()` 메소드를 사용하면 상수 이름을 출력할 수 있다.

> 열거 타입의 추가적인 기능과 특징

- 열거 타입에는 메서드나 필드를 추가할 수 있고, 열거 타입이 인터페이스를 구현하게 할 수도 있다.
    - 언제 열거 타입에서 메서드나 필드를 추가하여 사용할까?
    → 각 상수와 연관된 로직 또는 데이터까지 열거 타입 클래스 안에서 다루고 싶을 때
        - 예) `Mint` `ICECREAM`과 `MILK`를 함께 구매 했을때의 가격을 알고 싶다.
    - 열거 타입 상수 각각을 특정 데이터와 연결지으려면 생성자에서 데이터를 받아 인스턴스 필드에 저장하면 된다.
    
    <img width="500" alt="Untitled (2)" src="https://user-images.githubusercontent.com/48689213/172990390-34c1e786-bbff-42e1-9a57-4b00b7d8e13d.png">

- 열거 타입은 자신 안에 정의된 상수들의 값을 배열에 담아 반환하는 정적 메서드인 `values`를 제공한다.
    - 예) 민트맛 음식 다 주세요
    
    <img width="403" alt="Untitled (3)" src="https://user-images.githubusercontent.com/48689213/172990447-69fe4449-fc29-4fc5-a79e-fccb46e1ed6b.png">
    <img width="153" alt="Untitled (4)" src="https://user-images.githubusercontent.com/48689213/172990458-1c170a0b-2fc7-47f3-95cf-4e777cadcd31.png">

- 열거 타입은 근본적으로 불변이라 모든 필드는 `final` 이어야 한다.

> 열거 타입과 private/package-private 메서드

- 열거 타입을 선언한 클래스 혹은 그 패키지에서만 유용한 기능은 `private` 메서드 또는
`package-private` 메서드로 구현하는 것이 좋다.
- 이렇게 구현된 기능은 해당 열거 타입 상수를 선언한 클래스/패키지에서만 사용할 수 있다.
- 즉 일반 클래스와 마찬가지로 기능을 외부에 노출해야할 합당한 이유가 없다면
`private`/`package-private`으로 선언하라(`Item15` : 클래스와 멤버의 접근 권한을 최소화하라)

> 열거 타입과 클래스 레벨

- 널리 쓰이는 열거 타입은 `top-level 클래스`로 만들고, 특정 클래스에서만 사용 되는 열거 타입은 해당 클래스의 `멤버 클래스`로 만들자.
- 이러한 규칙을 지향함으로써 다양한 API가 더 일관된 모습을 갖출 수 있게 되었다!

## 열거 타입 사용의 확장

- 상수마다 적용되는 비즈니스 로직이 달라져야 하는 상황이 있을수도 있다.
    - 예) 계산기의 연산 종류를 열거 타입으로 선언하는 경우

> switch-case 문을 사용하는 경우

```java
enum Operation {
    PLUS, MINUS, TIMES, DIVIDE;
    
    public double apply(double x, double y) {
        switch(this) {
            case PLUS: return x + y;
            case MINUS: return x - y;
            case TIMES: return x * y;
            case DIVIDE: return x / y;
        }
        throw new AssertionError("알 수 없는 연산: " + this);
    }
}
```

- 동작은 하지만 그닥 예쁘지 않을 뿐더러 깨지기 쉬운 코드이다.
    - 새로운 상수가 추가되면 새로운 case 문을 추가해야 한다.
    - 새로운 case 문을 추가하는것을 깜빡하고 프로그램을 실행시키는 경우, 컴파일은 되지만 새로 추가한 연산을 수행하려 하면 `AssertionError`가 발생할 것이다.

> 상수별 메서드 구현 방법 : 추상 메서드를 사용하는 경우

```java
public enum Operation {
  PLUS {public double apply(double x, double y) {return x + y;}},
  MINUS {public double apply(double x, double y) {return x - y;}},
	TIMES {public double apply(double x, double y) {return x * y;}},
  DIVIDE {public double apply(double x, double y) {return x / y;}};

  public abstract double apply(double x, double y);
}
```

- 하나의 추상 메서드를 선언하고, 각 상수마다 자신에 맞게 해당 메서드를 재정의하여 사용하는 방법
- `메서드 재정의`와 `상수 선언`이 나란히 붙어 있으니 새로운 상수를 추가할 때 메서드 재정의를 깜빡하기 어려울 뿐더러, `추상 메서드`이므로 재정의 하지 않으면 컴파일 오류가 난다.
- 상수별 메서드 구현을 상수별 데이터와 결합할 수도 있다.
    
    ```java
    public enum Operation {
        PLUS("+") {
            public double apply(double x, double y) {return x + y;}
        },
        MINUS("-") {
            public double apply(double x, double y) {return x - y;}
        },
        TIMES("*") {
            public double apply(double x, double y) {return x * y;}
        },
        DIVIDE("/") {
            public double apply(double x, double y) {return x * y;}
        };
    
    		private final String symbol;
    
        Operation(String symbol) { this.symbol = symbol; }
    
        @Override
        public String toString() { return symbol; }
        public abstract double apply(double x, double y);
    ```
    
    - `toString`을 재정의하여 `상수값 이름` 대신 `연산 기호`를 반환하도록 만들어 주었다.
    - 계산식을 출력하는 것이 훨씬 간편해졌다!
    - 단, 열거 타입의 `toString` 메서드를 재정의하려거든, `toString` 이 반환하는 문자열을 해당 열거 타입 상수로 변환해주는 `fromString` 메서드도 함께 제공하는것을 고려해보자.
    
    ```java
    private static final Map<String, Operation> stringToEnum =
    		Stream.of(values()).collect(Collectors.toMap(Object::toString, e -> e));
    
    // 지정된 문자열에 해당하는 Operation이 존재하면 반환
    public static Optional<Operation> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }
    ```
    
    - 상수별 메서드 구현에서 열거 타입을 다룰 때 주의할 점
        - 열거 타입의 `static` 필드 중 열거 타입 생성자에서 접근할 수 있는 것은 static 필드 변수 뿐이다.
        - 열거 타입 생성자가 실행되는 시점에는 정적 필드들이 아직 초기화되기 전 이기 때문
        - 열거 타입 생성자에서 자기 자신 뿐만 아니라 같은 열거 타입 내부의 다른 상수에도 접근할 수 없다.
    - `fromString`을 사용할 때 주의할 점
        - `fromString`의 반환 타입은 Optional<Operation>이다.
        - 주어진 문자열이 가리키는 연산이 존재하지 않을 수 있음을 알리기 위함
- 상수별 메서드 구현의 단점
    - 열거 타입 상수끼리 코드를 공유하기 어렵다.
    - 즉, 동일한 연산을 하지만 모양만 다른 상수가 있다고 할 때, 하나의 상수에서 먼저 정의한 연산을 가져다가 사용할 수 없다. (동일한 연산을 수행하는 코드를 두 번 작성해 주어야 함)(재사용성 BAD)
        
        ```java
        enum PayrollDay {
        	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
        
        	private static final int MINS_PER_SHIFT = 8 * 60; // 하루 8시간
        
        	int pay(int minutesWorked, int payRate) {
            int basePay = minutesWorked * payRate;
        		int overtimePay;
        
        		switch(this) {
        			case SATURDAY:
        			case SUNDAY: // 주말
        				overtimePay = basePay / 2;
        				break;
        			default: // 주중
        				if (minutesWorked <= MINS_PER_SHIFT) overtimePay = 0;
        				else overtimePay = (minutesWorked-MINS_PER_SHIFT) * payRate / 2;
        		}
        
        		return basePay + overtimePay;
        
        	}
        }
        ```
        
        - ‘공휴일’ 을 나타내는 상수를 열거 타입에 추가하려면, 해당 값을 처리하는 case 문도 추가해야 한다.

> 전략 열거 타입 패턴을 사용하는 방법

- 위 상황처럼 열거 타입 상수 일부가 같은 동작을 공유할 때 사용하기 좋은 방법이다.
- 새로운 상수를 추가할 때, 해당 상수가 취할 동작인 `전략`을 선택할 수 있게 하는 것
    
    ```java
    enum PayrollDay {
      MONDAY(WEEKDAY),
      TUESDAY(WEEKDAY),
      WEDNESDAY(WEEKDAY),
      THURSDAY(WEEKDAY),
      FRIDAY(WEEKDAY),
      SATURDAY(WEEKEND),
      SUNDAY(WEEKEND);
    
      private final PayType payType;
    
      PayrollDay(PayType payType) { this.payType = payType; }
    
      int pay(int minutesWorked, int payRate) {
          return payType.pay(minutesWorked, payRate);
      }
    
      enum PayType {
          WEEKDAY {
              int overtimePay(int minsWorked, int payRate) {
                  return minsWorked <= MINS_PER_SHIFT ? 0 :
                          (minsWorked - MINS_PER_SHIFT) * payRate / 2;
              }
          },
    
          WEEKEND {
              int overtimePay(int minsWorked, int payRate) {
                  return minsWorked * payRate / 2;
              }
          };
    
          abstract int overtimePay(int mins, int payRate);
          private static final int MINS_PER_SHIFT = 8 * 60;
    
          int pay(int minsWorked, int payRate) {
              int basePay = minsWorked * payRate;
              return basePay + overtimePay(minsWorked, payRate);
          }
      }
    
      public static void main(String[] args) {
          for (PayrollDay day : values())
              System.out.printf("%-10s%d%n", day, day.pay(8 * 60, 1));
      }
    }
    ```
    
    - 휴일근무수당 계산을 `private` 중첩 열거 타입(`PayType`)으로 옮기고 `PayrollDay` 열거 타입의 생성자에서 이중 적당한 것을 선택한다.
    - 그러면 `PayrollDay` 열거타입은 휴일근무수당 계산을 해당 전략 열거 타입에 위임하여 `switch` 문이나 상수별 메서드 구현이 필요 없게 된다.
    - 이 패턴은 `switch` 문보다 복잡하지만 더 안전하고 유연하다.
- `switch` 문을 잘 활용하는 방법
    - `switch` 문은 열거 타입의 상수별 동작을 구현하는데에는 적합하지 않지만 기존 열거 타입에 상수별 동작을 혼합해 넣을 때, 혹은 가끔 쓰이지만 열거 타입 안에 포함시킬만큼 유용하지는 않은 경우 등에는 좋은 선택이 될 수 있다.

## 열거 타입을 언제 사용해야 할까?

- 필요한 원소를 컴파일 타임에 다 알 수 있는 상수 집합이라면 열거 타입으로 구현하자.
    - 예) 태양계 행성, 요일, 체스 말 등
- 허용하는 값들을 컴파일 타임에 이미 알고 있을 때에도 사용할 수 있다.
    - 예) 메뉴 아이템, 연산 코드, 명령줄 플래그 등
- 열거 타입에 정의된 상수 개수가 고정 불변일 필요는 없다.
    - 열거 타입은 나중에 상수가 추가되어도 바이너리 수준에서 호환되도록 설계되었기 때문
<br>
