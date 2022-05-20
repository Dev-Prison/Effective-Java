## 중첩 클래스

- 다른 클래스 안에 정의된 클래스.
- 중첩 클래스는 해당 클래스를 감싸고 있는 클래스 안에서만 사용될 수 있으며
그 외의 클래스에서 사용되기 위해서는 Top Level 클래스로 만들어야 한다.
- 중첩클래스의 종류
    - `정적 멤버 클래스`, `비정적 멤버 클래스`, `익명 클래스`, `지역 클래스`
    - 정적 멤버 클래스 빼고 모두 inner class에 해당한다?
       
       <img width="500" alt="1" src="https://user-images.githubusercontent.com/48689213/169430081-5819a574-f992-4108-9459-945adb063717.png">

        
- 각각의 중첩 클래스를 언제, 왜 사용해야 하는지 살펴보자.

<br>

## 정적 멤버 클래스

- 다른 클래스 내부에서 선언되고, 바깥 클래스의 private 멤버에 접근할 수 있다.
    
    ```java
    public class OuterClass {
    	
    	private String name;
    	
    		static class StaticMemberClass {
    	    void hello() {
    	        OuterClass outerClass = new OuterClass();
    	        outerClass.name = "홍길동";
    	  }
    	}
    }
    ```
    
- 위 특징을 제외하면 일반 클래스와 거의 동일하다.
- 일반적인 정적 필드와 동일한 접근 규칙을 갖는다
    - 예 : private 으로 선언하면 바깥 클래스에서만 해당 클래스에 접근할 수 있다.

### 정적 멤버 클래스의 사용 예시

- 바깥 클래스와 함께 쓰일 때만 유용한 public 도우미 클래스로 사용될 수 있다.
    - 예 : 계산기의 연산 종류를 나타내는 열거 타입 객체 `Operation`이 있다고 생각해보자.
    - `Operation` Enum은 `Calculator` 클래스의 **public 정적 멤버 클래스**가 되어야 한다.
    - 그래야 Calculator 클래스 객체가 `Calculator.Operation.PLUS`나 `Calculator.Operation.MINUS` 등의 형태로 필요한 연산을 참조할 수 있다.
        
        ```java
        public class Calculator {
        	public static enum Operation {
              PLUS("+", (x, y) -> x + y),
              MINUS("-", (x, y) -> x - y);
        			...
          }
        }
        ```
        
<br>

## 비정적 멤버 클래스

- 정적 멤버 클래스와 비교했을 때 코드 상의 차이는 `static` 이 붙어 있느냐 없느냐 뿐이지만
의미적으로 비교해보면 꽤 큰 차이가 있다.

### 비정적 멤버 클래스의 특징

- 비정적 멤버 클래스 객체는 바깥 클래스 객체와 암묵적으로 연결된다.
    - 비정적 멤버 클래스 객체의 메서드에서 정규화된 this(`클래스명.this`)를 이용해
    바깥 클래스 객체의 메서드를 호출하거나 바깥 클래스 객체의 참조를 가져올 수 있다.
    
    ```java
    class OuterClass {
      int x = 10;
    
      // 비정적 멤버 클래스
      public class InnerClass {
    		int x = 100;
        public void run() {
            System.out.println(OuterClass.this.x, this.x);
        }
      }
    
    	// 생성자 사용 방법 주목
    	public static void main(String[] args) {
    		OuterClass outer = new OuterClass();
    		OuterClass.InnerClass inner = outer.new InnerClass();
    		inner.run(); // 10, 100 출력
    	}
    }
    ```
    
    - 중첩 클래스의 객체가 바깥 클래스의 객체와 독립적으로 존재할 수 있다면 정적 멤버 클래스로 만들자
        - 비정적 멤버 클래스는 바깥 클래스 객체 없이는 생성할 수 없기 때문
    
    - 변수명이 겹치지 않으면 this 사용하지 않고 바로 참조하는 것도 가능
    
    ```java
    public class OuterClass{
    	int outerfield;
    	void outerMethod(){ System.out.println(outerfield); }
    
    	// 비정적 멤버 클래스
    	public class InnerClass {
    		void innerMethod() {
    			outerfield = 3;
    			outerMethod();
    		}
    	}
    }
    ```
    
- 비정적 멤버 클래스의 객체와 바깥 클래스 객체 사이의 이러한 암묵적인 관계는 멤버 클래스가 객체화될 때 확립되며 그 이후에는 변경할 수 없다.
    - 이 관계는 보통 바깥 클래스 객체의 메서드에서 비정적 멤버 클래스의 생성자를 호출할 때 자동으로 만들어지지만, 드물게는 직접 `바깥클래스.new 내부클래스(args)` 를 호출해 수동으로 만들기도 한다.
    - 이 관계 정보는 비정적 멤버 클래스 객체 안에 저장되어 메모리 공간을 차지하며, 생성 시간도 소모한다.

### 비정적 멤버 클래스의 사용 예시

- 비정적 멤버 클래스는 `어댑터`를 정의할 때 자주 쓰인다.
    - 어댑터 : 어떤 클래스의 객체를 감싸 마치 다른 클래스의 객체처럼 보이게 하는 것
        - 예) Map 인터페이스 : 자신의 컬렉션 뷰를 구현할 때 비정적 멤버 클래스를 사용한다.
        - 예) Set, List 같은 컬렉션 인터페이스 : 자신의 반복자를 구현할 때 비정적 멤버 클래스를 사용한다.
        
        ```java
        public class MySet<E> extends AbstractSet<E> {
        	...
        	@Override public Iterator<E> iterator() { return new MyIterator(); }
        	private class MyIteratpr implements Iterator<E> { ... }
        	...
        }
        ```
        

### 비정적 멤버 클래스의 단점

- 멤버 클래스에서 바깥 클래스 객체에 접근할 일이 없다면 무조건 static을 붙여서 정적 멤버 클래스로 만들자.
- static을 생략하면 바깥 객체로의 숨을 외부 참조를 갖게 된다.
    - 이 참조를 저장하는데 시간과 공간이 소모된다.
    - GC가 바깥 클래스의 객체를 수거하지 못하는 메모리 누수 문제가 생길수도 있다.
    - 참조가 눈에 보이지 않으니 문제 원인을 찾기가 어렵다.

### private 정적 멤버 클래스

- private 정적 멤버 클래스는 주로 바깥 클래스가 표현하는 객체의 한 부분을 나타낼 때 사용한다.
- 예) 키와 값을 매핑시키는 Map 객체
    - 많은 Map 구현체는 각각의 키-값 쌍을 표현하는 Entry 객체를 가지고 있다.
    - 모든 Entry가 Map과 연관되어 있지만 Entry의 메서드들(getKey, getValue, setValue 등)은 맵을 직접 사용하지는 않는다.
    - 즉 Entry를 비정적 멤버 클래스로 표현하는 것은 낭비이고, private 정적 멤버 클래스가 가장 알맞다.
    - Entry를 선언할 때 static을 빼먹어도 Map은 여전히 동작하지만 모든 Entry가 바깥 Map으로의 참조를 갖게 되어 공간과 시간을 낭비할 것이다.
- 멤버 클래스가 공개된 클래스의 public이나 protected 멤버라면 static이냐 아니냐의 여부는 2배로 중요해진다. 멤버 클래스 역시 외부에 공개되므로, 개발 도중 static이 붙으면 하위 호환성이 깨질 수 있다.

<br>

## 익명 클래스

- 이름이 없는 클래스. 멤버 클래스와 달리 쓰이는 시점에 선언과 객체 생성이 동시에 이루어진다.
    - 익명 클래스는 바깥 클래스의 멤버가 아니다.
    - 왜냐하면 사용되는 시점에 인스턴스화 되고, 코드상의 어떤 위치에서든 만들 수 있기 때문이다.
- 비정적인 문맥에서 사용될 때만 바깥 클래스의 객체를 참조할 수 있다.
- 정적 문맥에서 사용될 때는 static 변수 이외의 static 필드는 가질 수 없다.
    - 상수 표현을 위해 초기화된 final 기본 타입과 문자열 필드만 가질 수 있다.

### 익명 클래스의 단점

- 선언한 지점에서만 인스턴스를 만들 수 있다.
- `instanceof` 검사나 클래스의 이름이 필요한 작업은 수행할 수 없다.
- 여러 인터페이스를 구현할 수 없고, 인터페이스를 구현하면서 다른 클래스를 상속받을 수 없다.
- 익명 클래스를 사용하는 외부 클래스는 해당 익명 클래스가 상속받은 상위 클래스 외에는 호출할 수 없다.
- 표현식 중간에 등장하므로 짧지 않으면(10줄 이하) 가독성이 떨어진다.

### 익명 클래스의 사용 예시

- 자바가 람드를 지원하기 전에는 즉석에서 작은 함수 객체나 처리객체(Process Object)를 만드는데 익명 클래스를 주로 사용했다.
- 현재는 람다가 그 역할을 대신하고 있다.
- 정적 팩토리 메서드를 구현할 때 사용될 수 있다.
    
    ```java
    static List<Integer> intArrayAsList(int[] a) {
    
    	Objects.requireNonNull(a);
    	return new AbstractList<>() {
    		@Override
    		public Interger get(int i) { return a[i]; }
    	
    		@Override
    		public Integer set(int i, Integer val) {
    			int oldVal = a[i];
    			a[i] = val;
    			return oldVal;
    		}
    
    		@Override
    		public int size() { return a.length; }
    	}
    }
    ```
    
<br>

## 지역 클래스

- 네가지 중첩 클래스 중 가장 드물게 사용된다.
- 지역변수를 선언할 수 있는 곳이면 어디서는 선언할 수 있고 유효 범위도 지역변수와 동일하다.
- 다른 중첩 클래스들과의 공통점
    - 멤버 클래스처럼 이름이 있고 반복해서 사용할 수 있다.
    - 익명 클래스처럼 비정적 문맥에서 사용될 때만 바깥 클래스 객체를 참조할 수 있으며,
    정적 필드는 가질 수 없고 가독성을 위해 짧게 작성해야 한다.
