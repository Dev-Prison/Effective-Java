
# readObject 메소드는 방어적으로 작성하라

## readObject 메소드를 방어적으로 작성해야 할 필요성 - Java 역직렬화에선 어떤 일이 일어나는가?

### 역직렬화에선 객체 생성자가 호출되지 않는다.

Java 역직렬화를 사용할 경우, **“어떤 객체를 생성하기 위해, 생성자 는 호출 되지 않는**다”.

대신, **reflection 을 통해 필드들을 로딩**하게 된다.

- 이로 인해 발생하는 일
    - 만약 **우리가 생성자에 “검증 코드” 를 작성해 놓았더라도 무용지물**이 된다는 것이다.

즉,  **Serializable 을 구현한 객체일 경우**,

- ***생성자 뿐만 아니라***
- ObjectInputStream 의 `**readObject()` 을 통한 *역직렬화로 객체를 “생성” 하는 경우* 또한** 존재한다.
- **실질적으로 또 다른 public 생성자**를 갖고 있는 것이다.
-

따라서 **“생성자” 뿐만 아니라, readObject 메소드 역시 방어적으로 작성할 필요**가 있다.

# 예시 ) 생성자와 접근자 메소드를 방어적으로 작성하여 불변객체로 구현한 경우

Item 50 에서 보았던 예시다.

Date 가 가변 객체이기 때문에 , 가변객체를 필드로 갖는 Period 를 불변객체로 만들어 주기 위해 아래와 같은 “방어적 복사”, “검증” 을 수행했다.

```c
public final class Period {
	private final Date start;
	private final Date end; 

    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end   = new Date(end.getTime());

        if (this.start.compareTo(this.end) > 0)
            throw new IllegalArgumentException(
                    this.start + " after " + this.end);
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }
}
```

- 생성자
    - 방어적으로 복사
    - 검증 수행
- 접근자
    - 방어적 복사

이를 Serializable 으로 선언하기만 해도 불변식을 깨트리게 되는 것이다.

## 해결책 1  : readObject 작성하기

따라서 readObject 에서

- 역직렬화를 수행 한 이후
- **검증을 수행**

하도록 할 수 있다

```java
private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();

    if (this.start.compareTo(this.end) > 0) {
        throw new IllegalArgumentException(
            this.start + " after " + this.end);
    }
}
```

- defaultReadObject : 역직렬화 시키려는 클래스의 readObject 메소드에서 호출되는 메소드다. 해당 클래스에 대한 스트림으로부터 비정적 , non transien 필드들을 읽어온다. 즉, 역직렬화 시 non-static, not -transient 필드들 을 세팅 해 주게 된다 .

## 해결책1 에 존재하는 문제

( 책의 코드 자체는 잘 이해가 되지 않았으나, 문제가 되는 부분은 아래와 같았습니다 )

> 위의 defaultReadObject 를 사용한다면, **직렬화 할 당시의 Period 인스턴스가 가진 start, end 와 동일한 start, end 를 참조하고 있게** 된다
>

```java
class DeserializeTest {

    private final Period existingPeriod = new Period(new Date(1000L), new Date(1500L));

		@Test
    @DisplayName("직렬화를 통해 바이트 스트림을 생성한 기존의 객체가 지닌 필드의 참조값과, 역직렬화를 통해 생성한 객체가 지닌 필드의 참조값은 동일하다")
    void givenObject_whenSerializedAndDeserilized_thenSameReferenceField() {
        byte[] bytes = SerializationUtil.serialize(existingPeriod);

        Period deserializedPeriod = (Period) SerializationUtil.deserialize(bytes);

        Assertions.assertThat(deserializedPeriod.endCode())
                .isEqualTo(existingPeriod.endCode());
    }
}
```

따라서 **이를 악용👿 하여**

“ 이러한 Period 객체로부터 직렬화를 통해 생성된 스트림” 에 추가적인 스트림을 추가해, **Period 내부 객체의 가변 객체에 대한 참조값을 “ 가져올 수 있도록 하는 경우 → *해당 스트림을 통해 Period 내부에서 참조하고 있는 Date 객체를 수정하는 것이 가능해*진다.**

```java
public class MutablePeriod {
    
   public final Period period;
   public final Date start;
   public final Date end;

   public MutablePeriod() {
       try {
           ByteArrayOutputStream bos = new ByteArrayOutputStream();
           ObjectOutputStream out = new ObjectOutputStream(bos);

           // 1. 새로운 Period 객체를 생성하고 -> "직렬화" 한다 
           out.writeObject(new Period(new Date(), new Date()));

					// 악의적인 바이트 스트림을 주입 
					// 어떤 바이트 스트림? -> Period 
           byte[] ref = { 0x71, 0, 0x7e, 0, 5 }; // 악의적인 바이트스트림
           bos.write(ref);                       
           ref[4] = 4;                           // 악의적인 바이트스트림
           bos.write(ref);                       

           // 역직렬화 과정에서 Period 객체의 Date 참조를 훔친다.
           ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
           
           period = (Period) in.readObject();
           start  = (Date) in.readObject();
           end    = (Date) in.readObject();
           
       } catch (IOException | ClassNotFoundException e) {
           throw new AssertionError(e);
       }
   }
}
```

```java
		MutablePeriod mp = new MutablePeriod();
    Period p = mp.period;
    Date pEnd = mp.end;

    pEnd.setYear(78);      // end 필드를 80년으로 수정한다.
    System.out.println(p);
        
    pEnd.setYear(69);      // end 필드를 60년으로 수정한다.
    System.out.println(p);
```

### 현재 상황의 문제점

**해결책 1은 readObject 에 “검증코드” 만을 추가**한 것이다.

> 참조 필드에 대한 ***“방어적 복사” 를 안 했었다.***
>

## 해결책 2 : 역직렬화 코드에, 불변객체의 참조 필드에 대한 “방어적 복사” 코드를 작성하자

즉, Period 가 불변객체이도록 하기 위해 작성했던 “생성자 코드” 처럼

- ***방어적 복사***
- 검증

코드를 갖도록 역직렬화 메소드인 readObject 를 수정해야 한다.

```java
private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();

    // 방어적 복사
    start = new Date(start.getTime());
    end   = new Date(end.getTime());

    // 검증
    if (start.compareTo(end) > 0) {
        throw new InvalidObjectException(start + "가 " + end + "보다 늦다.");
    }
}
```

- 하지만 ***readObject 는 “생성자가 아니기” 때문에, start, end 는 final 일 수 없게 된***다

# 결론

> **public 생성자 처럼 주의해야 할 점들이 있는 것이다.**
>
> - 어떤 byte stream 이 인자로 오더라도 “유효한 인스턴스” 를 생성해야 한다.
- transient 필드들을 제외하고, **어떤 필드에다가 유효성 겁사 없이 값을 대입하는 public method 를 추가하는 것이 *~~“괜찮지 않다면”~~*** → 커스텀 readObject 를 통해 **“유효성 검사” + “방어적 복사” 를 수행**하자.
    - **방어적 복사 —→ 유효성 검사(ex- 불변식 검사)** 를 하자
    - **또는  Item 90 에 나오는 “프록시 패턴” 을 사용**할 수 있다 ( ***안전한 역직렬화 를 위한 이런 코드가 타겟 객체에 직접 삽입되지 않을 수 있겠다*** )
    - 괜찮다면 기본 readObject 를 사용해도 될 것 같다. 책의 예제는 불변객체 여야 했기에 , 우리는 이를 허용해선 안되었다.
- final 클래스가 아닌 경우, **readObject 내부에서는 “재정의 될 수 있는 메소드를 직,간접적으로 호출 해서는 안된다”**
    - 생성자에서도 마찬가지였다.
    - **하위 클래스에서 재정의된 메소드로 인한 오작동 가능성이 존재**한다.


# 참조

[https://snyk.io/blog/serialization-and-deserialization-in-java/](https://snyk.io/blog/serialization-and-deserialization-in-java/)