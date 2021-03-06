## π¨What

### νκ·Έ λ¬λ¦° ν΄λμ€

- λκ°μ§ μ΄μ κΈ°λ₯μ κ°κ³  μμΌλ©° κ·Έμ€μμ μ΄λ€ κΈ°λ₯μ κ°κ³  μλμ§ λνλ΄λ tag νλκ° μλ ν΄λμ€λ₯Ό μλ―Ένλ€.

```java
class Figure {
    enum Shape { RECTANGLE, CIRCLE };

    final Shape shape; // νκ·Έ νλ - νμ¬ λͺ¨μμ λνλΈλ€.

    // λ€μ νλλ€μ λͺ¨μμ΄ μ¬κ°ν(RECTANGLE)μΌ λλ§ μ°μΈλ€.
    double length;
    double width;

    // λ€μ νλλ λͺ¨μμ΄ μ(CIRCLE)μΌ λλ§ μ°μΈλ€.
    double radius;

    // μμ© μμ±μ
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // μ¬κ°νμ© μμ±μ
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

## βWhy

- **μ΄κ±°(enum) νμ μ μΈ, νκ·Έ νλ, switch λ¬Έμ₯ λ± μΈλ°μλ μ½λκ° λ§λ€.**
- **μ¬λ¬ κ΅¬νμ΄ νλμ ν΄λμ€μ νΌν©λΌ μμ΄μ κ°λμ±λ μ’μ§ μλ€.**
    - λ§μ½ μ¬λ¬κ°μ§λΌλ©΄ λΉλ‘μ μΌλ‘ μ½λμ λν κΈΈμ΄μ§ κ²μ΄λ€.
- **λ€λ₯Έ μλ―Έλ₯Ό μν μ½λκ° ν¨κ» μμΌλ μλμ μΌλ‘ λ©λͺ¨λ¦¬λ λ μ°¨μ§νλ€.**
    - μλ₯Ό λ€μ΄ μ κ°μ²΄λ₯Ό μ¬μ©νμ§λ§ μ¬κ°νμ λ©λͺ¨λ¦¬λ ν¨κ» μ‘νκ² λ¨.
- **νλλ₯Ό finalλ‘ μ μΈνλ €λ©΄ ν΄λΉ μλ―Έμ μ¬μ©λμ§ μλ νλκΉμ§ μμ±μμμ μ΄κΈ°νν΄μΌ νλ€**.
    - λ°λμ λ°μμΌν  νλκ° μ’λ₯λ§λ€ μλ€λ©΄ nullμ΄λ 0μΌλ‘ μ΄κΈ°νν΄νλ€.
    - μ°μ§ μλ νλλ₯Ό μ΄κΈ°ννλ μ½λκ° μκΈ΄λ€.
- **λ λ€λ₯Έ μλ―Έλ₯Ό μΆκ°νλ €λ©΄ μ½λλ₯Ό μμ ν΄μΌ νλ€.**
    - νΉν switch λ¬Έμ₯μλμΈμ€ν΄μ€μ νμλ§μΌλ‘λ νμ¬ λνλ΄λ μλ―Έλ₯Ό νμνκΈ° μ΄λ ΅λ€.

## βHow (κ°μ )

- κ³μΈ΅ κ΅¬μ‘°λ‘ λ°κΎΈκΈ° νΉμ Interfaceλ‘ λμ λ§μΆκΈ°

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

- λ©λͺ¨λ¦¬ μ¬μ©λ μ μ½
- μ½λ κ°λμ± μ¦κ°

<details>
<summary>Detail</summary>
<div markdown="1">       

**κ°κ²°νκ³  λͺνν΄μ§**

- **μΈλ°μλ μ½λλ€μ΄ λͺ¨λ μ¬λΌμ‘λ€.**
- μ’λ₯κ° λμ΄λ  λλ§λ€ λΉλ‘μ μΌλ‘ μ½λλμ΄ μ¦κ°νμ§ μλλ€.

κ° μλ―Έλ₯Ό λλ¦½λ ν΄λμ€μ λ΄μκΈ° λλ¬Έμ κ΄λ ¨ μλ λ°μ΄ν° νλλ λͺ¨λ μ κ±° λμλ€.

κ²λ€κ° μ€μλ‘ λΉΌλ¨Ήμ switch κ΅¬λ¬Έμ case λ¬Έμ₯ λλ¬Έμ λ°νμ μ€λ₯κ° λ°μν  λ²κ·Έλ μλ€.

νμ μ¬μ΄μ μμ°μ€λ¬μ΄ κ³μΈ΅ κ΄κ³λ₯Ό λ°μν  μ μμ΄μ μ μ°μ±μ λ¬Όλ‘  μ»΄νμΌ νμμμμ νμ κ²μ¬ λ₯λ ₯λ λμ¬μ€λ€.

- λΉν¨μ¨μ μΈ λ©λͺ¨λ¦¬ μ¬μ© μΈ‘λ©΄μμ λ¦¬μμ€λ₯Ό μ μ½ν  μ μλ€.
- κ³μΈ΅ κ΅¬μ‘°λΌλ©΄, μλμ κ°μ΄ μ μ¬κ°ν(Square)κ° μΆκ°λ  λλ κ°λ¨νκ² λ°μν  μ μλ€.

</div>
</details>