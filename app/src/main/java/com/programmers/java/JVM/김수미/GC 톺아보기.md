## GC(Garbage Collector)란 무엇인가?

- 자바는 객체를 생성하는 new 연산자는 있지만 객체를 소멸시키는 연산자는 없다.
    - 개발자가 직접 객체를 소멸시킬 수가 없다.
- 객체를 소멸시킨다는 것의 의미는, new에 의해 생성된 객체가 차지하고 있던 공간을 JVM에게 돌려주어
다시 사용할 수 있는 메모리 영역으로 만들어 준다는 것을 의미한다.
- new로 할당받은 이후 사용하지 않게 된 객체 혹은 배열 메모리를 Garbage 라고 부른다.
- 이러한 Garbage를 정리하는것이 Garbage Collector의 역할이며, 적절한 시점에 자동으로 Garbage를 수집하여 가용 메모리에 반환한다.
    - GC의 스케줄링은 JVM에서 관리한다.

<img width="849" alt="Untitled" src="https://user-images.githubusercontent.com/48689213/165878521-61bf9b62-6758-4c2a-8d41-114117c30126.png">

- 객체는 Heap 영역에 저장되고, Stack 영역에 이를 가리키는 주소값이 저장된다.
- Heap 영역에는 존재하지만 Stack영역에서 해당 객체를 가리키는 주소값이 없는 객체를 ‘참조되지 않는
(자신을 가리키는 포인터가 없는, unreachable)객체’ 라고 이야기한다.
- GC는 해당 객체를 더 이상 사용되지 않는 객체라고 간주하여 이를 메모리에서 제거한다.
<br><br><br>

## GC의 필요성

- GC가 없다면?
    - C/C++ 언어처럼 일일이 메모리 해제를 해줘야 한다.
- 메모리 해제를 해주지 않는다면?
    - 코드가 돌아갈수록 메모리 가용 공간을 하나씩 차지함에 따라, 속도 등의 프로그램 성능이 매우 악화된다.
- 개발자가 직접 메모리 해제가 가능한 C++ 언어에서도 GC 기능을 제공한다.
    - 메모리 관리에 있어서 프로그래머의 부담을 덜어주기 때문.
        - 메모리 관리를 프로그래머가 직접 할 수 있지만, 부담이 크고 생산성이 떨어진다.
    - new와 delete 만으로는 이룰 수 없는 무언가가 있다 → **메모리 파편화 현상**
        - new와 delete를 반복하면서 heap메모리에 사용하는 메모리와 사용하지 않는 메모리가 듬성듬성 위치하는 현상으로, 메모리 사용 효율 저하의 원인이 된다.
        - GC는 힙을 직접 관리하기 때문에 메모리 파편화 현상을 일정부분 해결할 수 있다.
        - GC가 동작하는 동안 사용하지 않는 메모리를 정리하면서 힙 메모리의 빈부분을 최적화 할 수 있음
<br><br><br>

## GC의 종류

### Serial GC

- GC를 처리하는 스레드가 1개
- CPU 코어가 1개만 있을 때 사용하는 방식이다.
- **Mark-Compact collection** 알고리즘을 사용해 GC를 실행한다.

### Parallel GC

- GC를 처리하는 스레드가 여러개
- Serial GC보다 빠르게 객체 처리가 가능하다.
- 메모리가 충분하고 코어의 개수가 많을 때 사용하면 좋다.

### **Concurrent Mark Sweep GC(CMS GC)**

- **Stop-The-World**
    - GC를 실행하기 위해 JVM이 애플리케이션 실행을 멈추는 것
    - stop-the-world가 발생하면 GC를 실행하는 스레드를 제외한 나머지 스레드는 모두 작업을 멈춤
    - GC 작업을 완료한 이후에 중단한 작업을 다시 시작
- stop-the-world 시간이 짧다.
- 애플리케이션의 응답 시간이 빨라야 할 때 유용하다.
- 다른 GC 방식보다 메모리와 CPU를 더 많이 사용한다.
- Compaction 단계가 제공되지 않는다.

### G1 GC

- 각 영역을 Region 영역으로 나눈다.
- GC가 일어날 때, 전체 영역(Eden, Survivor, Old generation)을 탐색하지 않는다.
- 바둑판 각 영역에 객체를 할당하고 GC를 실행한다.
- 해당 영역이 꽉 차면 다른 빈 영역에 객체를 할당하고 GC 실행한다.
- stop-the-world 시간이 짧다.
- **Compaction 단계**를 제공한다.

### Z GC

- 확장 가능하고 낮은 지연율(low latency)을 가진 GC
    - 정지 시간이 최대 10ms를 초과하지 않는다.
- Heap의 크기가 증가하더라도 정지 시간이 증가하지 않는다.
    - 8MB~16TB에 이르는 다양한 범위의 Heap 처리가 가능하다.
- Stop-The-World 정지 시간을 줄이거나 없앰으로써 애플리케이션의 성능 향상에 기여한다.
    - ZGC의 주요 원리는 **Load barrier**와 **Colored Pointer**를 함께 사용하는 것
    - 이를 통해 Thread가 동작하는 중간에도 객체 재배치 같은 작업을 수행할 수 있다.
- Z GC는 메모리를 Z Pages라고 불리는 영역으로 나눈다.
    - ZPages는 동적 사이즈(G1 GC와 다름)로 2MB의 배수가 동적으로 생성 및 삭제될 수 있다.
<br><br><br>

## GC의 장단점

- `장점` 편리성
    - 개발자가 동적으로 할당된 메모리 전체를 관리할 필요가 없다.
    - 유효하지 않은 포인터에 접근하거나 이미 한번 해제한 메모리를 또 해제하는 등의 버그를 피할 수 있다.
- `단점` 예측 불가능성
    - 가비지 컬렉션이 수행되는 정확한 시점을 알 수가 없다.
    - 가비지 컬렉션이 실행될 때는 반드시 애플리케이션을 중지시키는 Stop The World가 수행되는데
    이는 성능 저하의 원인이 될 수 있는 Overhead 를 일으킨다.
        - 프로그램이 예측 불가능하게 일시 정지 될 수 있기 때문에, 실시간 시스템에는 적합하지 않다.
<br><br><br>

## GC의 메모리 수거 방식

- 앞에서 정리했듯이, GC는 동적으로 할당한 메모리 영역 중 사용하지 않는 영역을 탐지하여 해제한다.
    - Stack : 정적으로 할당한 메모리 영역
        - 원시 타입의 데이터 값과 함께 할당, Heap영역에 생성된 Object 타입의 데이터 참조 값 할당
    - Heap : 동적으로 할당한 메모리 영역
        - 모든 Object 타입의 데이터가 할당, Heap영역의 Object를 가리키는 참조 변수가 Stack에 할당
- **Heap에 저장된 객체를 Stack에서 참조하고 있는지 어떻게 확인할까? : Mark and Sweep**
    - 가비지 컬렉터가 Stack의 모든 변수를 스캔하면서, 각각 객체를 참조하고 있는지 확인 후 마킹한다 (Mark)
    - Reachable Object가 참조하고 있는 객체도 찾아서 마킹한다 (Mark)
    - 마킹되지 않은 객체를 Heap에서 제거한다 (Sweep)

![Untitled (1)](https://user-images.githubusercontent.com/48689213/165878446-b5ebc9b6-35a4-4b73-9aad-345a3d2ea188.png)

### Eden

- 새로운 객체가 할당되는 영역
- Eden 영역의 메모리가 전부 사용중인 경우 GC 발생 (**Minor GC**)

 → Eden 영역에서 Mark and Sweep

### S0(Survivor 0)

- Eden 영역의 Reachable 객체가 S0 영역으로 옮겨짐
- Eden 영역의 Unreachable 객체는 메모리에서 해제
- S0 영역의 메모리가 전부 사용중인 경우 GC 발생 (**Minor GC**)

 → S0 영역에서 Mark and Sweep

### S1(Survivor 1)

- S0 영역에 있던 객체는 S1 영역으로 이동
- 이동한 객체는 Age 값 증가
- Eden 영역의 Reachable 객체가 S1 영역으로 옮겨짐
- Eden 영역의 Unreachable 객체는 메모리에서 해제
- S1 영역의 메모리가 전부 사용중인 경우 GC 발생 (**Minor GC**)

 → S1 영역에서 Mark and Sweep

### S0(Survivor 0)

- S1 영역에 있던 객체는 S0 영역으로 이동
- 이동한 객체는 Age 값 증가
- Eden 영역의 Reachable 객체가 S0 영역으로 옮겨짐
- Eden 영역의 Unreachable 객체는 메모리에서 해제
- S0 영역의 메모리가 전부 사용중인 경우 GC 발생 (**Minor GC**)

 → S0 영역에서 Mark and Sweep

💡 `위의 과정을 계속 반복한다`

### Old Generation

- Age 값이 특정 값 이상이 되면 Old Generation 영역으로 이동
    - 이 과정을 'Promotion' 이라고 한다.
- Old Generation 영역이 모두 사용중인 경우 GC 발생 (**Major GC**)

 → Old Generation 영역에서 Mark and Sweep

💡 `위의 과정 또한 계속 반복된다`

- 만약 Young(New) Generation 영역과 Old Generation 영역이 모두 사용중인 경우 GC 발생 (**Full GC**)

 → 전체 영역에서 Mark and Sweep
 
💡 `S0 영역과 S1 영역 중 한쪽은 반드시 비어있다.`

### Permanent Generation

- 해당 영역은 GC의 관리영역이 아니다.
- JVM이 애플리케이션에서 사용하는 클래스 및 메소드를 설명하는 데 필요한 메타데이터를 포함하는 영역
- 애플리케이션에서 사용 중인 클래스에 기반하여, 런타임에 JVM에 의해 채워진다.
- Java SE 라이브러리 클래스 및 방법을 여기에 저장할 수 있다.
