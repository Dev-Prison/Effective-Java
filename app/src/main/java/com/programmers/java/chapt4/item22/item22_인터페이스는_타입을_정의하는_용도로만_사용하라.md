## 인터페이스는 타입을 정의하는 용도로만 사용하라

* 인터페이스는 자신을 구현한 클래스의 인스턴스를 참조할 수 있는 타입 역할을 한다.
* 클래스가 어떤 인터페이스를 구현한다는 것은 자신의 인스턴스로 무엇을 할 수 있는지를 클라이언트에 얘기해 주는 것이다.
* 인터페이스는 이 용도로만 사용해야한다.
```java
List<String> names = new ArrayList<>();
```

### 지침에 맞지 않는 상수 인터페이스의 예
* 상수 인터페이스란 메서드 없이 상수를 뜻하는 static final 필드로만 가득 찬 인터페이스를 말한다. 이 상수들을 사용하려는 클래스에서는 정규화된 이름을 쓰는 걸 피하고자 그 인터페이스를 구현하곤 한다.
```java
public interface PyhsicalConstatns {
    // 아보가드로 수 (1/몰)
    static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    // 볼츠만 상수 (J/K)
    static final double BOLTZMANN_CONSTATNT = 1.380_648_52e-23;
    // 전자 질랑 (kg)
    static final double ELECTRON_MASS = 9.109_383_56e-31;
}
```
* 이 상수인터페이스를 구현하는 것은 내부 구현을 API로 노출하는 행위이다.
* 내부구현임에도 불구하고 클라이언트가 이 상수들에 종속하게 된다.
* 상수들이 쓰이지 않더라도 바이너리 호환성을 위해 상수 인터페이스를 구현하고 있어야 한다.
* final이 아닌 클래스가 상수 인터페이스를 구현한다면 모든 하위 클래스의 이름공간이 그 인터페이스가 정의한 상수들로 오염이 되버린다.
* java.io.ObjectStreamConstants등 자바 플랫폼 라이브러리에도 상수 인터페이스가 몇개 있지만 인터페이스를 잘못 활용한 예기 때문에 따라하면 안된다.

### 해결방법
* 특정 클래스나 인터페이스와 강하게 연관된 상수라면 그 클래스나 인터페이스 자체에 추가해야 한다.
  * 모든 숫자 기본 타입의 박싱 클래스가 대표적으로 Integer와 Double에 선언된 MAX_VALUE, MIN_VALUE가 이런 예시이다.
* 열거 타입으로 나타내기 적합한 상수라면 열거 타입으로 만들어 공개하면 된다.
```java
public enum PhysicalConstantsEnum {
    AVOCADOS_NUMBER(6.022_140_857e23),
    BOLTZMANN_CONSTANT(1.380_648_52e-23),
    ELECTRON_MASS(9.109_383_56e-31);

    private final double value;

    PhysicalConstantsEnum(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
```
* 이것도 아니라면 인스턴스화 할 수 없는 유틸리티 클래스에 담아 공개하도록 해야한다.
  * 유틸리티 클래스에 정의된 상수를 클라이언트에서 사용하려면 클래스 이름까지 함께 명시해야 한다.
  * 유틸리티 클래스의 상수를 빈번히 사용한다면 정적 임포트 하여 클래스 이름을 생략할 수 있다.
```java
public class PyhsicalConstatns {
    private class PyhsicalConstatns() {}
    // 아보가드로 수 (1/몰)
    public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    // 볼츠만 상수 (J/K)
    public static final double BOLTZMANN_CONSTATNT = 1.380_648_52e-23;
    // 전자 질랑 (kg)
    public static final double ELECTRON_MASS = 9.109_383_56e-31;
}
```


> 인터페이스는 타입을 정의하는 용도로만 사용해야 한다. 상수 공개용 수단으로 사용하지 말자 !