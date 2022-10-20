이펙티브 자바 + 밸둥(발등?baeldung) 내용 바탕으로 정리해 보았습니다. 약간 개인의 추론적인 내용도 들어있어서 의문스러운 부분에 대해 지적해주시면 감사 하겠슴다!

# 동기화 ?

## **원자성 이 보장되는 연산이라면 , 무조건 동기화가 되는 것이라 볼 수 있을까 ? → NO!**

우리는 동기화의 의미에 대해 생각해 볼 필요가 있다.

- **‘배타적 실행’** 을 위한 것
    - ( Critical Section 을 **한 번에 하나의 job 에서만 접근(read,write) 가능**하도록 하는 것 )
- 동기화는 ‘ **어떤 스레드 가 *만들고 있는 변화를*, *다른 스레드에서 확인 하지 못 하도록* ‘ 하는 기능**이 있다. ( 트랜잭션 이 떠오른다 )
    - **일관되지 못한 순간의 상태를, 다른 스레드에서 볼 수 없도록** 한다 → **그 이전까지 반영된 상태는 다른스레드에서 볼 수 있**도록 한다.
    - 그렇다면.. 이 말은  **어떤 스레드 가 완료시킨 변화는, 다른 스레드에서 확인 할 수 있게 된 다는 것**을 의미하는 것과도 같다고 생각한다.
- 어떤 스레드 가 어떤 변화를 완료 시킨다면 **“하나의 일관된 상태 → 다른 일관된 상태” 로 변화** 시킨 것일 것이다.
- 이번 아이템 에서는 여기에 집중한다
    - **동기화가 *없으면,* 한 스레드에서 만든 변화를 다른 스레드에서 확인 못 할 수도** 있다. ( 완료시킨 변화임에도 다른 스레드에서 확인을 못한다고??? 왜지..???? ?? 뒤에서 차차 살펴본다!! )

***공유 중인 가변 데이터를 원자적 으로 읽고 쓸 수*** 있더라도, ***동기화에 실패한다면*** 어떤 일이 일어날까?

## 예시)원자적인 연산에 대해 동기화하지 않은 경우 발생하는 일과 그 원인 ( Reordering, Cache coherence)

다음과 같이, **공유중인 가변 데이터가 존재하는 상황**이 있다. 여기서는 이 공유중인 가변 데이터에 대해 동시에 원자적인 연산을 하려고 한다.

메인 스레드에서, 또 다른 스레드를 실행하고 있다.

멀티코어 시스템이라면, 동시에 실행되는 이 멀티 스레드들 중 일부가 병렬로 실행되고 있을 것이다.

새롭게 생성된 스레드 내부에서는 `**stopRequested**` 값에 대해 polling 하는 것을 볼 수 있다.

```java
private class StopThread {
    private static booleanstopRequested;

    public void run() throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested) {
                i++;
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
				stopRequested= true;
    }
}
```

그런데 여기서 !

- **Java 언어 명세상, boolean 필드에 대한 read, write** 는 **원자적인 작업이다.  ( 즉, 기계어로 번역 되더라도 여러개의 instruction 으로 이루어져 있지는 않을 것**이다 < — > **이와 상반되게, 뒤에서 보겠으나 증감 연산자는 원자적이지 않은 작업**이다 `++, —` 연산자 )

아무튼 그렇다면 우리는 이런 예상을 할 것이다.

> 얼마 안 있어 프로그램은 종료 되겠지? 메인 스레드에서 true 로 업데이트해주기만 하면 되는거고, 실제 메인스레드에서 실행하는 부분에는 stopRequested  상태를 true 로 업데이트 해 주고 있으니까!
>

하지만 *내 컴퓨터에서 위의 코드는 무한 루프를 돌고 있었다*.

- 와 근데 이거는 static 공간이라서 그런 것 같기도..

  객체에 대해서 하니까 정상 종료함 ㄸ; ?


앞서 살짝 언급된 부분이다.

> **동기화가 없으면, 한 스레드에서 만든 변화를 다른 스레드에서 확인 하는 것이 언제가 될지 보증 할 수 없다.**
>

그 원인은 다음과 같다.

- 1️⃣  ***컴파일러에서 이런식의 최적화(hoisting)를 함으로서  Rerodering(순서 재배치) 일어날 수도 있다.***
    - 즉, 우리가 ***프로그래밍 한 코드 순서대로! 실행이 되지 않을 수도!*** 있다는 것이다.

    ```java
    while(!stopRequested){
    	i++;
    }
    ```

  위와 같은 코드가, 컴파일러 최적화에 의해 아래와 같이 될 수가 있다.

    ```java
    if(!stopRequested) {
    	while(true){
    		i++;
    	}
    }
    ```

  이 경우, 메인 스레드에서 초기화 되었던 stopRequested 상태 값 만으로  조건을 검증하고, while 루프를 계속 돌게 되기 때문에 무한 루프에 빠지게 된다.

- 2️⃣  또한, **프로세서의 cache 로 인한 Memory visibility 와 관련 되어 있을 수도** 있다( 간단히 얘기하면 , **write 작업을 즉시 처리하지 않고, 프로세서의 캐시에 write buffering** 을 하다 보니, **메모리에 실제로 write 하는 것은 더 느리다**. 이에 따라 각 스레드를 실행 시키는 코어들의 캐시 사이에 데이터 일관성 불일치가 생기는 것이다.)

결과적으로  *예상한 동작이 일어나지 않을 수도 있다*

## 동기화를 하자. 그런데 “쓰기” 만이 아닌 “읽기”도 !

> **쓰기, 읽기 모두가 동기화 되지 않으면, 동작 을 보장하지 않는다!!**
>

### 방법 1 ) synchronized 사용 → **공유 변수에 대한 접근 영역에 대해 synchronized 를 붙여**주면 된다

```java
public class StopThread {

    private static boolean stopRequested;
		
		// write
    private static synchronized void requestStop() {
				stopRequested= true;
    }
		
		// read 
    private static synchronized boolean stopRequested() {
        return stopRequested;
    }

    public static void main(String[] args)
        throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested()) {
                i++;
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
				requestStop();
    }
}
```

### 방법 2 ) volatile 사용 → 변수 자체 선언시 volatile 키워드를 붙인다 → 그럼, **항상 가장 최근에 기록된 값을 읽게 됨이 보장**

> volatile 키워드를 붙일 경우 다음과 같은 일이 일어나기 때문이다
>
- **volatile 변수가 포함된 instruction 에 대한 reordering 이 일어나지 않게** 한다.
- **프로세서들은, volatile 변수에 대한 모든 업데이트를 즉각적으로 (→ 메모리로) flush 한다**.

```java
public class StopThread {
    private static volatile boolean stopRequested;

    public static void main(String[] args)
            throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested)
                i++;
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
```

## 하지만, 원자적 연산이 아닌 증감연산자의 경우는 volatile 으론 해결되지 않는다

> 💥 **WARNING** — ***volatile ≠ atomic!***
>
> - **volatile 은 다른 코어에도 해당 변화에 대한 visibility 를 제공할 뿐**이다.
> - **AtomicInteger 는 visibility 와 atomicity 모두를 제공**한다.

### volatile 키워드에만 의존한 증감연산 예시

위의 예시의 경우에는, JAVA 언어 명세상 boolean 필드에 대한 읽기, 쓰기 작업 자체가 원자적 이었다.

하지만 만약, **원자적인 연산이 아닌 경우라면 ? 필드를 volatile 로 선언한 것으로는 충분하지 않게** 된다.

예를들어 다음과 같이, 항상 유니크한 시리얼 넘버를 생성하는 목적의 generateSerialNumber() 메소드가 있다고 하자.

```java
private static volatile int nextSerialNumber = 0;

public static int generateSerialNumber() {
	return nextSerialNumber++;
}
```

하지만 모두 알겠지만 **증감 연산자는 내부적으로 두가지 연산이 일어 난다(** 원자적 연산이 여러개 필요하다 )

따라서 *우리가 예상한대로 동작하지 않는다*( 여러 스레드에서 **중복된 시리얼 넘버를 얻어간다** )

```java
class SerialNumberTest {
    private static volatile intserialNumber1= 0;
		public static int generateSerialNumber1() {
        return serialNumber1++;
    }

		@Test
    @DisplayName("일반 int 인 volatile 설정된 시리얼 넘버에 대하여, 0 부터 시작하여 x 번 만큼 serialNumber 를 통해 수를 얻어오는 스레가 동시에 다수 실행되는 경우, 다음 번 serialNumber 는 x 가 아닐 수도 있다")
    void givenVolatileInteger_whenIncrement_thenFail () throws InterruptedException {
        int x = 1000;

        ExecutorService threadPoolExecutor =
            Executors.newFixedThreadPool(10);

        IntStream.range(0,x)
            .forEach(i ->
                threadPoolExecutor.submit(SerialNumberTest::generateSerialNumber1));

        Thread.sleep(2000);

        Assertions.assertThat(generateSerialNumber1())
            .isNotEqualTo(x);
    }
}
```

그 이유는 무엇일까?

- volatile 은 그저 변경된 값을 바로바로 메모리로 flush 를 해 주도록 할 뿐이다.
- 연산 자체가 원자적이지 않기 때문에, 결국은 동시성 이슈가 생기는 것이다.

### 후위 증감 연산자 에서 일어나는 과정을 atomic 하게 만들어준다면?

후위 증감연산은 실제로

```java
a++; // 라는 것을 할 경우 내부적으로

// 대충 아래와 같은 과정을 거치게 된다
tempA = a;
tempA = tempA + 1; 
a = tempA; 
```

즉 내부적으로 원자적이지 못하다.

그런데 JAVA 역시, **이 일련의 과정 자체가 원자적으로 일어날 수 있도록 하는 “타입”** 이 존재한다.  → `**AtomicXXX**`

```java
class SerialNumberTest {
    private static volatile AtomicLong nextSerialNumber = new AtomicLong();
		public static long generateSerialNumber2() {
        return nextSerialNumber.getAndIncrement();
    }

    @Test
    @DisplayName("AtomicInteger 인 volatile 설정된 시리얼 넘버에 대하여, 0 부터 시작하여 x 번 만큼 serialNumber 를 통해 수를 얻어오는 스레가 동시에 다수 실행되는 경우더라도, 다음 번 serialNumber 는 x 임을 기대한다")
    void givenVolatileAtomicInteger_whenIncrement_thenSuccess() throws InterruptedException {
        int x = 1000;

        ExecutorService threadPoolExecutor =
            Executors.newFixedThreadPool(10);

        IntStream.range(0,x)
                .forEach(i ->
                    threadPoolExecutor.submit(SerialNumberTest::generateSerialNumber2));

        Thread.sleep(2000);

        Assertions.assertThat(generateSerialNumber2())
            .isEqualTo(x);
    }
}
```

### AtomicXX

AtomicInteger 와 같은 것을 사용할 경우, 개발자는 직접 synchronized 와 같은 lock 을 사용한 구현을 하지 않아도 동기화 가 가능하다.

## 멀티스레드에서 안전하게 데이터 공유하기 위해선 ( effectively immutable, safe publication )

다음과 같은 두가지 방식을 제시하고 있다.

이에 대해서는 자세한 설명이 없길래 간단하게 찾아보았다. (근데 이해가 안되는 부분 다수 존재)

- **effectively immutable**
    - immutable 과의 차이가 뭐지?
        - 불변객체라고 하면 보통 1. 확장 가능하지 않음 2. 모든 필드는 final 로 선언하여 → 객체 생성시 초기화 되고, 이후에 변경할 수 없다.
        - final 로 선언되지 않았지만, 메소드 구현을 통해 초기화 이후 변경될 수 없도록 구현된 것을 effectively immutable 이라고 부르는 것 같다. —→ 여기에는, 메소드를 통해 해당 필드를 리턴할 때 마다, 새로운 객체로 복사해서 리턴하는 방법이 해당된다. 왜냐하면 이렇게 만들 경우, 해당 객체의 상태 자체는 외부에서 변경하는 것이 불가능해지기 때문이다.  —> 즉, 동시에 실행중인 스레드 각각에서 접근하는 객체들이 결국 서로 다른 객체 이도록 하는 것을 의미하는 것 같다.( 🤔❓ 잘 모르겠다)
- **safe publication 테크닉?**
    - **all the values written before the publication visible to all readers that observed the published object ( 그 객체가 발행되기 이전에 일어난 모든 변화들이, 해당 객체를 구독하고 있는 독자들에게 visible 해야 한다**
    - **→ 앞에서 계속 말해왔던 동기화와 visiblity 에 대한 개념 같다. 그래서 이 테크닉 들을 찾아보면**
        - synchronized
        - volatile
        - Atomic

      등등이 나오게 된다. 한 번 찾아보자 ~


의 방식을 제시하고 있다.

## 결론

> **가변 데이터는, 단일 스레드에서만 사용**하자.
>

> 가변 데이터를 **공유한다면, 해당 데이터에 대한 읽기/쓰기 작업은 반드시 동기화** 하자. (다른 스레드에서도 볼 수 있도록! )
>
>
> 배타적 실행까지 필요한게 아니라면, volatile 을 통해, 스레드 간의 통신이 잘 동기화 되는 것이라도 할 수 있다. ( 하지만 volatile 을 잘 사용하는 것 역시 매우 까다롭다고 한다 )
>
- increment 처럼 내부적으로 여러 연산이 일어나는 경우가 존재한다는 것을 보았다.
- 따라서 사용하는 프레임워크, 라이브러리가 내부적으로 어떤 연산이 일어나는지 깊이 아는 것이 좋다.

## 참조

[https://www.baeldung.com/java-volatile](https://www.baeldung.com/java-volatile)

[https://brooker.co.za/blog/2012/11/13/increment.html](https://brooker.co.za/blog/2012/11/13/increment.html)

[https://shipilev.net/blog/2014/safe-public-construction/](https://shipilev.net/blog/2014/safe-public-construction/)

[https://shipilev.net/blog/2014/safe-public-construction/](https://shipilev.net/blog/2014/safe-public-construction/)