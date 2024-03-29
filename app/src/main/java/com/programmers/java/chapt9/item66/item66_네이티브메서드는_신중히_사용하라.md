# 네이티브 메소드

## JNI?

Java Native Interface 는 자바 프로그램세어 Native method 를 호출하는 기술이다.

네이티브 메소드는 /C,C++ 로 작성한 메소드

## Native method 를 사용하던 곳 ( 하지만 점차 변화 중)

- **플랫폼 특화 기능**이 필요할 때 ***(ex.시스템 콜)***
    - 하지만 Java 의 발전으로 인해 , **플랫폼 기능을 대체할 자바 라이브러리 들이 많이 생겨 나고 있다.**
    - ex) JAVA 9 의 process API 는 OS process 에 접근이 가능하다.
- 네이티브 코드로 작성된 레거시 라이브러리가 필요 할 때
- 성능에 결정적인 영향을 주는 부분
    - 이 **역시 JVM 의 발전이 있어 왔기 때문에, 네이티브 구현보다 훨씬 나은 성능을 제공 해 주기도** 한다.
    - 따라서 실질적으로 네이티브 메소드 사용이 성능 개선 해 주는 것은 드물다
- GNU 다중 정밀 연산 라이브러리(GMP) 필요 시.

## Java 와 비교 시 네이티브 메소드 의 단점

- **안전하지 않다**
    - Item 50 참조!
- **이식성이 낮다**
    - Java 는 이식성이 높은 언어다. 해당 플랫폼에 적합한 JVM 만 있다면 플랫폼 독립적인 바이트 코드를 실행 하는 것이 가능하다. → 따라서 native method 를 사용하면, 해당 프로그램 자체의 이식성이 낮아진다.
    - 반면, 해당 플랫폼에서 컴파일된 C , C++ 프로그램은 해당 플랫폼 에서 만 실행 가능하다.
- **Native memory 는 GC 에 의해 자동 회수 되지 못하며 추정도 불가능하다.**
- **Java 코드와 네이티브 코드의 경계를 넘나드는데 비용이 든다.**
    - Java 코드와 네티이브 코드 사이의 “접착 코드(glue code)” 가 필요하다. ( 추가 작업이 필요하다 )

# 결론

성능 향상을 기대하며 네이티브 메소드 사용을 고려하는 경우가 많을 것이다. 하지만 실질적으로 네이티브 메소드가 성능 향상을 가져다 주는 경우는 매우 드물기 때문에 함부로 사용해서는 안 된다.

따라서 최소한의 네이티브 메소드를 사용하고, 이에 대해 철저히 테스트 해야 한다.