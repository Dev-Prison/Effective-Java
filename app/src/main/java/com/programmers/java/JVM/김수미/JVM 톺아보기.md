[> Notion 바로가기 <](https://www.notion.so/DAY-8-JVM-157d19f299b94c498dc756232111ffc6)

## 들어가기에 앞서

- 왜 JVM을 공부해야 하는가?
    - 메모리 구조를 알아야 메모리 관리가 잘 되는 코드를 작성할 수 있기 때문
- 왜 메모리 관리를 해야 하는가?
    - 메모리 관리에 따라 프로그램의 성능은 월등하게 차이날 수 있다.
    - 메모리 부족으로 인한 성능저하 현상은 대부분 메모리에 대한 이해없이 코드를 설계한 경우 발생한다.
<br>

## JVM의 개념과 특징

- Java는 특정 운영체제에 종속되지 않도록 JVM이라는 가상머신 위에서 실행되게끔 만들어진 언어
    - `운영체제가 바뀌면?` Java 코드는 그대로, JVM 만 다른것을 사용하면 OK
- `JVM(Java Virtual Machine)` 자바와 운영체제 사이에서 중재자 역할을 수행하는 가상 머신
    - 자바가 운영체제에 구애 받지 않고 프로그램을 실행할 수 있도록 도와준다.
    - 자동으로 메모리 관리를 해준다. (GC)
    - 다른 하드웨어와 달리 레지스터 기반이 아닌 스택 기반으로 동작한다는 점이 특징
<br>

## `.java` 프로그램이 실행되는 과정

- `자바 컴파일러`에 의해 `자바 소스 파일`이 바이트 코드 형태의 `클래스 파일`로 변환된다.
- `클래스 로더`가 해당 `클래스 파일`을 읽어들여 JVM 내에 객체를 로드하고 배치한다.
    - 리눅스 환경에서 만든 자바 파일을 윈도우에서 실행하고 싶다면 **윈도우용 JVM**을 설치하면 된다.
<br>

## JVM 구성 요소

- **Class Loader**
    - 컴파일 결과로 만들어진 `.class` 바이트코드 파일을 읽어들여 메모리에 배치
    - 로딩, 링크, 초기화 세 가지 과정을 거친다.
        - 런타임 시에 동적으로 클래스를 로드한다.
    
- **Execution Engine**
    - 클래스 로더를 통해 Runtime Data Area에 배치된 바이트 코드들을 명령어 단위로 읽어서 실행한다.
    - 초기 JVM은 인터프리터 방식이었기 때문에 속도가 느리다는 단점이 있었지만, 바이트 코드를 어셈블러 같은 네이티브 코드로 바꿔주는 JIT 컴파일러 방식을 통해 이를 보완했다.
        - 그러나 JIT를 통해 코드를 변환하는데에도 비용이 발생함
        - 때문에 JVM은 모든 코드를 JIT 컴파일러 방식으로 실행하지 않고, 인터프리터 방식을 사용하다가 일정기준이 넘어가면 JIT 컴파일러 방식으로 실행한다.
        
- **Garbage Collector**
    - 힙 메모리 영역에 저장된 객체들 중 더이상 참조되지 않는 객체들을 탐색 및 제거하는 역할을 수행
    - GC 동작하는 정확한 시간은 예측할 수 없다.
    
- **Runtime Data Area**
    - JVM 메모리
    - Java 어플리케이션이 실행되는 와중에 할당받은 메모리영역이다.
    - **Runtime Data Area의 주요 4가지 영역**
        
        ### **Method area (Meta Space)**
        
        - 모든 쓰레드가 공유하는 메모리 영역
        - 클래스, 인터페이스, 메소드, 필드, Static 변수 등의 바이트 코드를 보관
        
        ### **Heap area**
        
        - 모든 쓰레드가 공유하는 메모리 영역
        - new 키워드로 생성된 객체와 배열이 생성되는 영역
        - Method area에 로드된 클래스만 생성 가능
        - Garbage Collector가 작동하는 영역
        
        ### **Stack area (Thread Stack)**
        
        - Method를 호출할 때 마다 각각의 스택 프레임(그 메서드만을 위한 공간)이 생성된다.
        - Method의 parameter, 반환값, 지역변수등을 저장하는 용도로 사용된다.
        - Method의 동작이 끝나면 해당 스택 프레임은 삭제된다.
        
        ### **PC Register**
        
        - 쓰레드가 시작될 때 생성되는 메모리로, 쓰레드마다 PC Register가 ****하나씩 존재한다.
        - 쓰레드가 어떤 부분을 무슨 명령으로 실행해야할 지에 대한 정보를 기록하는 부분
        - 현재 수행중인 JVM 명령의 주소를 가진다.
        
        ### **Native method stack**
        
        - Java 외의 언어(C/C++)로 작성된 네이티브 코드를 위한 메모리 영역
            - Java Native Interface를 이용하여 네이티브 코드 작성 가능
            - **네이티브 코드를 사용하는 이유**
                - 하나의 Java 파일은 해당 운영체제 환경에 상관없이 똑같은 결과를 반환해야 한다.
                (운영체제에 따라 서로 다른 JVM이 존재하기 때문에 이것이 가능)
                    - 단점 : 운영체제의 모든 기능을 JVM이 담지 못한다
                        - 리눅스에서만 돌아가는 코드를 허용하면 윈도우즈에서는 안돌아가는 상황 발생
                    - 따라서 운영체제의 고유한 일부 기능은 Java 언어만으로 구현이 불가능하다.
        
        ### **Code Cache**
        
        - JIT 컴파일러가 데이터를 저장하는 영역
        - 자주 접근하는 '컴파일된 코드 블록'이 저장된다.
        - 일반적으로 JVM은 바이트 코드를 기계어로 변환하는 작업을 수행하는데, 이곳에 저장된 코드는 기계어로 이미 변환된 채 캐시되어 있으므로 빠르게 실행할 수 있다.
        
        ### **Shared Library**
        
        - 애플리케이션에서 사용할 공유 라이브러리가 기계어로 변환되어 저장되는 영역
        - OS에서 프로세스당 한 번씩 로드된다.
<br>

## Class Loader의 개념과 동작 원리

- 클래스 로더는 동적 로딩(Dynamic Loading)을 통해 필요한 클래스들을 런타임 데이터 영역(Runtime Data Area), 즉 JVM 메모리 위에 올린다.
    - 로드 : 클래스 파일을 가져와서 JVM의 메모리에 로드
    - 검증 : 자바 언어 명세 및 JVM 명세에 명시된 대로 구성되어 있는지 검사
    - 준비 : 클래스가 필요로 하는 메모리를 할당 (필드, 메서드, 인터페이스 등)
    - 분석 : 클래스의 상수 풀 내 모든 심볼릭 레퍼런스를 다이렉트 레퍼런스로 변경
    - 초기화 : 클래스 변수들을 적절한 값으로 초기화 (static 필드)
<br>

## Java 메모리 할당 방식

```java
public static void main(String[] args) {
	int number = 12;
	Mydata mydata = new Mydata(15);
}
```

- JVM은 기본적으로 Stack과 Heap 두가지 저장 공간에 메모리를 할당한다.
- 간단한 변수인 Primitive Type의 변수들은 Stack에 저장이 가능하지만, 복잡한 변수는 Heap 공간에 저장되고 Stack에는 해당 Heap 공간을 가리키는 변수가 저장된다.
    - JVM은 코드 한 줄 씩 읽으며 객체를 메모리에 할당한다.
    - 읽어들인 줄에 저장해야 할 객체가 있을때, 객체가 Primitive Type이면 값 자체가 Stack에 저장된다.
    - 그 외는 Stack에는 객체의 주소값만 저장되고, 실제 값은 Heap 영역에 저장된다.
<br>
