## 💨What

### 태그 달린 클래스

- 두가지 이상 기능을 갖고 있으며 그중에서 어떤 기능을 갖고 있는지 나타내는 tag 필드가 있는 클래스를 의미한다.

```java
class Figure {
    enum Shape { RECTANGLE, CIRCLE };

    final Shape shape; // 태그 필드 - 현재 모양을 나타낸다.

    // 다음 필드들은 모양이 사각형(RECTANGLE)일 때만 쓰인다.
    double length;
    double width;

    // 다음 필드느 모양이 원(CIRCLE)일 때만 쓰인다.
    double radius;

    // 원용 생성자
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // 사각형용 생성자
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    double area() {
        switch(shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
```

## ❓Why

- **열거(enum) 타입 선언, 태그 필드, switch 문장 등 쓸데없는 코드가 많다.**
- **여러 구현이 하나의 클래스에 혼합돼 있어서 가독성도 좋지 않다.**
    - 만약 여러가지라면 비례적으로 코드양 또한 길어질 것이다.
- **다른 의미를 위한 코드가 함께 있으니 상대적으로 메모리도 더 차지한다.**
    - 예를 들어 원 객체를 사용하지만 사각형의 메모리도 함께 잡히게 됨.
- **필드를 final로 선언하려면 해당 의미에 사용되지 않는 필드까지 생성자에서 초기화해야 한다**.
    - 반드시 받아야할 필드가 종류마다 있다면 null이나 0으로 초기화해한다.
    - 쓰지 않는 필드를 초기화하는 코드가 생긴다.
- **또 다른 의미를 추가하려면 코드를 수정해야 한다.**
    - 특히 switch 문장에도인스턴스의 타입만으로는 현재 나타내는 의미를 파악하기 어렵다.

## ✅How (개선)

- 계층 구조로 바꾸기 혹은 Interface로 동작 맞추기

```java
abstract class Figure {
    abstract double area();
}

class Circle extends Figure {
    final double radius;
    Circle(double radius) { this.radius = radius; }
    @Override double area() { return Math.PI * (radius * radius); }
}

class Rectangle extends Figure {
    final double length;
    final double width;
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    @Override double area() { return length * width; }
}

public interface Voucher{
	long discount(long before);
}

class Fix implements Voucher{
	long discount(long before){
				// body .. 
  }
}

class Fix implements Voucher{
	long discount(long before){
				// body .. 
  }
}
```

### E.E (Expect Effect)

- 메모리 사용량 절약
- 코드 가독성 증가

<details>
<summary>Detail</summary>
<div markdown="1">       

**간결하고 명확해짐**

- **쓸데없는 코드들이 모두 사라졌다.**
- 종류가 늘어날 때마다 비례적으로 코드량이 증가하지 않는다.

각 의미를 독립된 클래스에 담았기 때문에 관련 없던 데이터 필드는 모두 제거 되었다.

게다가 실수로 빼먹은 switch 구문의 case 문장 때문에 런타임 오류가 발생할 버그도 없다.

타입 사이의 자연스러운 계층 관계를 반영할 수 있어서 유연성은 물론 컴파일 타임에서의 타입 검사 능력도 높여준다.

- 비효율적인 메모리 사용 측면에서 리소스를 절약할 수 있다.
- 계층 구조라면, 아래와 같이 정사각형(Square)가 추가될 때도 간단하게 반영할 수 있다.

</div>
</details>