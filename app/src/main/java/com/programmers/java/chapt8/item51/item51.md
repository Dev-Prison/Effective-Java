# Item51. 메서드 시그니처를 신중히 설계하라

## 스터디 날짜

2022/07/22


# API 설계 요령

## 1. 메서드 이름을 신중히 짓자
### 1. 의도가 분명해야 한다
```java
// bad
public List<int[]> getThem() { ... }
// good
public List<int[]> getFlaggedCells() { ... }
```
### 2. 다음 질문을 생각하라
- 왜 존재해야 하는가
- 무슨 작업을 하는가
- 어떻게 사용하는가
```java
public List<Piece> findPiecesByColor(Color color){ ... }
// 왜 존재해야 하는가 - color에 대해 존재하는 piece들을 알기 위해.
// 무슨 작업을 하는가 - color에 맞는 piece들을 가져온다.
// 어떻게 사용하는가 - 체스판에서 흑색(or 백색)의 piece들을 가져와서 점수를 계산.
```
### 3. 메서드 이름은 보통 동사/전치사로 시작한다
- 메서드명은 기본적으로는 동사로 시작한다. 
- 다른 타입으로 전환하는 메서드나 빌더 패턴을 구현한 클래스의 메서드에는 전치사를 쓸 수 있다.
```java
// 동사
public void getUserByName(){ ... }
public void setDisplayName(){ ... }
public void inputData(String input){ ... }
    
// 전치사
public String toString(){ ... }
public User of(){ ... }
```
### 4. 메서드이름으로 자주 사용되는 동사에 익숙해지자
- `get/set`
  - getter/setter 자바빈 규약
  - 하지만 OOP 에서 가장 해로운 요소중 하나이다.
  - set 을 통해 데이터가 언제 바뀔지 모른다
  - get 보다는 객체에 메세지를 보내자
- `init`
  - 데이터를 초기화하는 메서드 명에 쓰인다.
```java
public void initData(){ ... }
```
- `is/has/can`
  - boolean 값을 리턴한다.
```java
public boolean isNumber(){ ... }
public boolean hasData(){ ... }
public boolean canOrder(){ ... }
```
- `create`
  - 새로운 객체를 만든 후 리턴한다.
```java
public Board create(){ ... }
```
- `find`
  - 데이터를 찾는다
- `to`
  - 해당 객체를 다른 형태의 객체로 변환
- `A-by-B`
  - B를 기준으로 A 를 하겠다는 메서드명
```java
public Element findElement(int number){ ... }
public String toString(){ ... }
public void getUserByName(String name){ ... }
```
- 메서드 명명에 대해 자세히 알아보자

[자바 명명 규칙](https://m.blog.naver.com/reona7140/221306141987)

---
## 2. 편의 메서드를 너무 많이 만들지 마라
- 모든 메서드는 각자의 책임을 수행한다
- 메서드가 너무 많은 클래스는 너무 많은 책임을 가진다.
- 단일책임원칙을 생각하라
---
## 3. 매개변수 목록은 짧게 유지하자
- 매개변수 개수는 4개 이하를 유지하라
- 같은 타입의 매개변수가 여러개 나올경우 개발자는 쉽게 실수한다
  - 인텔리제이가 변수타입을 제공해준다
  - 빌더 패턴 적용시 위의 실수를 방지할 수 있다.
- 긴 매개변수 목록을 짧게 줄여주는 기술 3가지가 있다.
### 1. 여러 메서드로 쪼갠다
- 메서드가 너무 많아질 수 있지만, 직교성을 높여 오히려 메서드 수를 줄여주는 효과도 있다.
- "직교성이 높다."라는 뜻은 공통점이 없는 기능들이 잘 분리되어 있다는 것을 의미한다.
```java
findIndex(int fromIndex, int toIndex, Object element);

List<E> subList(int fromIndex, int toIndex);
int indexOf(Object o);
```

### 2. 매개변수 여러개를 묶어주는 도우미 클래스를 만들어라
- 매개변수 몇 개를 독립된 하나의 개념으로 볼수 있을 때 추천하는 기법이다. 
- 일반적으로 정적 멤버 클래스로 구현한다.
```java
// before
public void findMapLocation(int x, int y) {
        ...
}
```
```java
// after
public void findMapLocation(Location location) {
        ...
}
private static class Location {
  int x;
  int y;
}
```

### 3. 객체 생성에 사용한 빌더 패턴을 메서드 호출에 응용한다.
1. 모든 매개변수를 추상화한 객체를 만든다.(2번에서 다룬 Location과 같은 객체)
2. 클라이언트에서 setter를 호출해 값을 설정한다.
3. execute 메서드로 설정한 매개변수의 유효성을 검사한다.
4. 설정 완료된 객체를 넘겨 원하는 계산을 수행한다.


---
## 4. 매개변수 타입으로는 클래스보다 인터페이스를 사용하라
- 매개변수로 적합한 인터페이스가 있다면 그 인터페이스를 사용하자.
- ex) 매개변수로 HashMap을 받기보다 Map으로 받자.
  - TreeMap, ConcurrenthashMap 등 어떤 Map 구현체라도 인수로 넘길 수 있다.
```java
public void somefunction(Map map);
```
- 다형성을 통해 코드의 유연성을 극대화할 수 있다.

---
## 5. boolean 보다는 원소 2개짜리 열거 타입이 낫다.
```java
public enum TemperatureScale {FARENHEIT, CELSIUS}

// 어떤게 더 명확한가?
Thermometer.newInstance(true);
Thermometer.newInstance(TemperatureScale.CELSIUS);

```
- 열거 타입을 사용하는게 더 좋다. 
- 코드를 읽고 쓰기가 더 쉬워지기 때문이다. 
- 물론 예외는 존재한다
  - 만약 메서드 이름상 boolean을 받아야 더 의미가 명확한 경우라면 boolean을 사용하자.