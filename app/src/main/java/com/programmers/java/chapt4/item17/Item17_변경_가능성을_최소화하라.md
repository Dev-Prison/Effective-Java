## 불변 가능성을 최소화하라
* 불변 클래스란?
    * 인스턴스의 내부 값을 수정할 수 없는 클래스다.
    * 불변 인스턴스에 간직된 정보는 고정되어 객체가 파괴되는 순간까지 절대 달라지지 않는다.
* 자바 플랫폼이 제공하는 불변 클래스 종류
    * String, BigInteger, BigDecimal 등이 있다.
* 불변 클래스의 특징
    * 가변 클래스보다 설계하고 구현하고 사용하기 쉬우며, 오류가 생길 여지도 적고 훨씬 안전하기 때문이다.

<hr>

## 불변 클래스를 만드는 다섯가지 규칙

### 1. 객체의 상태를 변경하는 메서드(변경자)를 제공하지 않는다.
* 대표적으로 setter가 존재한다.

### **2. 클래스를 확장할 수 없도록 한다.**
* 하위 클래스에서 부주의하게 혹은 나쁜 의도로 객체의 상태를 변하게 만드는 사태를 막아준다.
* 상속을 막는 대표적인 방법은 클래스를 final로 선언하는 것이지만 다른 방법들도 존재한다.

> 생성자를 private 혹은 package-private 으로 만들고, public 정적 팩터리를 제공하는 방법이다.
```java
public class Complex {

    private final double re;
    private final double im;

    private Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex valueOf(double re, double im) {
        return new Complex(re, im);
    }
}
```

#### 재정의가 가능한 클래스의 위험성
```java
public class BigInteger extends Number implements Comparable<BigInteger> {
    ~~~
}

// 재정의 후 불변객체가 아닌데 불변객체라 생각하고 사용하면 문제가 생긴다.
class BiBiBigInteger extends BigInteger {
    ~~~~
}

// 따라서 진짜 불변 클래스인지 확인 후 사용을 해야한다.
public static BigInteger safeInstance(BigInteger val) {
    return val.getClass() == BigInteger.class ?
        val : new BigInteger(val.toByteArray());    
}
```

### **3. 모든 필드를 final로 선언한다.**
* 시스템이 강제하는 수단을 이용해 설계자의 의도를 명확히 드러내는 방법이다.
* 새로 생성된 인스턴스를 동기화 없이 다른 스레드로 건네도 문제없이 동작하게끔 보장하는 부분에도 필요하다.

### **4. 모든 필드를 private로 선언한다.**
* 필드가 참조하는 가변 객체를 클라이언트에서 직접 접근해 수정하는 일을 막아준다.
* 기술적으로는 기본 타입 필드나 불변 객체를 참조하는 필드를 public final로만 선언해도 불변 객체가 되지만, 이렇게 하면 다음 릴리스에서 내부 표현을 바꾸지 못하므로 권하지는 않는다.

```java
public final class Person {

    private final String name;
    private final PhoneNumber phoneNumber;
    
}
```

### **5. 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.**
* 클래스에 가변 객체를 참조하는 필드가 하나라도 있다면 클라이언트에서 그 객체의 참조를 얻을 수 없도록 해야한다.
* 이런 필드는 절대 클라이언트가 제공한 객체 참조를 가리키게 해서는 안되며, 접근자 메서드가 그 필드를 그대로 반환해도 안된다.
* 생성자, 접근자, readObject 메서드 모두에서 방어적 복사를 수행하라.
```java
public class PhoneNumber {

    private String phoneNum;
    
    // 생성자, 게터, 세터 ...
}


public class Person {

    private final String name;
    private final PhoneNumber phoneNumber;

    public Person(String name, PhoneNumber phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // 게터, 세터 ...
}

void immutable_test() {
    PhoneNumber phoneNumber = new PhoneNumber("010-1111-1111");

    Person person = new Person("형욱", phoneNumber);
    String beforeNumber = person.getPhoneNumber().getPhoneNum().toString();

    phoneNumber.setPhoneNum("010-2222-2222");
    String afterNumber = person.getPhoneNumber().getPhoneNum().toString();

    System.out.println(beforeNumber); // 010-1111-1111
    System.out.println(afterNumber); // 010-2222-2222
}

// 보완하기
public class Person {

    private final String name;
    private final PhoneNumber phoneNumber;

    public Person(String name, PhoneNumber phoneNumber) {
        this.name = name;
        this.phoneNumber = new PhoneNumber(phoneNumber.getPhoneNum());
    }

    // 게터, 세터 ...
}
```

<hr>

## 불변객체의 장점

* 불변 객체는 근본적으로 스레드 안전하여 따로 동기화할 필요가 없다.
  * 객체의 변동이 없기 때문에 여러 쓰레드에서 접근해도 절대 훼손될 일이 없다.

* 한번 만든 인스턴스는 재활용이 가능하다.
  * 정적 팩터리 메서드(아이템1)을 이용해 제공이 가능하다.(BigDecimal)

```java

public static BigDecimal valueOf(long val) {
    if (val >= 0 && val < ZERO_THROUGH_TEN.length)
    return ZERO_THROUGH_TEN[(int)val];
    else if (val != INFLATED)
    return new BigDecimal(null, val, 0, 0);
    return new BigDecimal(INFLATED_BIGINT, val, 0, 0);
    }
    
    private static final BigDecimal ZERO_THROUGH_TEN[] = {
        new BigDecimal(BigInteger.ZERO,       0,  0, 1),
        new BigDecimal(BigInteger.ONE,        1,  0, 1),
        new BigDecimal(BigInteger.TWO,        2,  0, 1),
        new BigDecimal(BigInteger.valueOf(3), 3,  0, 1),
        new BigDecimal(BigInteger.valueOf(4), 4,  0, 1),
        new BigDecimal(BigInteger.valueOf(5), 5,  0, 1),
        new BigDecimal(BigInteger.valueOf(6), 6,  0, 1),
        new BigDecimal(BigInteger.valueOf(7), 7,  0, 1),
        new BigDecimal(BigInteger.valueOf(8), 8,  0, 1),
        new BigDecimal(BigInteger.valueOf(9), 9,  0, 1),
        new BigDecimal(BigInteger.TEN,        10, 0, 2),
    }
```
* 방어적 복사가 필요 없다.
  * 복사를 해도 원본이랑 같기 때문에 굳이 clone 메서드나 복사 생성자를 제공하지 말자.
> 이러한 경우에는 final로 지정해도 안의 값들은 가변이기 때문에 clone을 이용해야 하지 않을까?
```java
public class Person {

    private final String name;
    private final PhoneNumber[] phoneNumbers;

    public PhoneNumber[] getPhoneNumbers() {
        return phoneNumbers.clone();
    }
}
```
* 불변 객체는 자유롭게 공유할 수 있음은 물론, 불변 객체 끼리는 내부 데이터를 공유할 수 있다.

* 상태가 변하지 않기 때문에 불일치 상태에 빠질 가능성이 없다.

* 다른 불변 객체들을 구성요소로 사용하면 이점이 많다.

<hr>

## 불변 객체의 단점
* 값이 다르면 반드시 독립된 객체로 만들어야 한다.
    * 값의 가짓수가 많다면 많은 비용이 들게된다.
    * 그럴 경우엔 가변 동반 클래스를 제공하도록 하자.

### 가변동반 클래스란?
* 불변 클래스와 거의 동일한 기능을 가지고 있지만, 가변적인 클래스를 가변 동반 클래스라고 한다.
* 가변 동반 클래스는 주로 불변 클래스가 비즈니스 로직 연산 등 시간 복잡도가 높은 연산시 불필요한 클래스 생성을 막기위해 내부적으로 사용한다.
* 대표적으로 불변 클래스인 String은 가변 동반 클래스인 StringBuilder가 있다.

<hr>

## 정리
* 게터가 있다고 해서 무조건 세터를 만들지는 말자.
* 클래스는 꼭 필요한 경우가 아니라면 불변으로 만들자.
    * 불변 클래스는 장점이 많으며 단점이라곤 특정 상황에서의 잠재적 성능 저하 뿐이다.
* 무거운 값 객체도 불변으로 만들 수 있을지 고심해야 한다. 
  * 성능 때문에 어쩔 수 없다면 불변 클래스와 쌍을 이루는 가변 동반 클래스를 public 클래스로 제공하도록 하자.
* 모든 클래스를 불변으로 만들 수는 없다.
    * 불변으로 만들 수 없는 클래스라도 변경할 수 있는 부분을 최소한으로 줄이자.
* 객체가 가질 수 있는 상태의 수를 줄이면 그 객체를 예측하기 쉬워지고 오류가 생길 가능성도 줄어든다.
  * 꼭 변경해야 할 필드를 뺀 나머지를 모두 final로 선언하자.
      * 즉 **다른 합당한 이유가 없다면 모든 필드는 private final이어야 한다.**
