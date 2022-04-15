# item 13 - clone 재정의는 주의해서 진행하라

## What

<aside>
⭐ Cloneable 인터페이스는 상속받은 클래스가 복제해도 되는 클래스임을 명시하는  인터페이스이다. Cloneable 인터페이스의 역할은 Object의 clone의 동작 방식을 결정한다. Cloneable을 상속한 클래스의 clone 메소드를 호출하면 해당 클래스의 필드 단위로 복사하여 반환한다. 만약, Cloneable을 상속받지 않고 clone 메소드를 호출하였다면 `'CloneNotSupportedExcetion'` 을 던진다.</aside>

### Object의 clone 메소드의일반 규약

1. x.clone() != x 는 참이다. 원본 객체와 복사객체는 서로 다른 객체이다.
2. x.clone().getClass() == x.getClass() 는 참이다. 하지만 반드시 만족해야 하는 것은 아니다.
3. x.clone().equals(x) 는 참이지만 필수는 아니다.
4. x.clone().getClass() == x.getClass(), super.clone()을 호출해 얻은 객체를 clone 메소드가 반환한다면, 이 식은 참이다. 관례상, 반환된 객체와 원본 객체는 독립적이어야 한다. 이를 만족하려면 super.clone으로 얻은 객체의 필드 중 하나 이상을 반환 전에 수정해야 할 수도 있다.
- 공식문서 정의
    - 이 개체의 복사본을 만들고 반환합니다.
    - "복사"의 정확한 의미는 개체의 클래스에 따라 다를 수 있다.
    - 일반적인 의도는 모든 객체 x에 대해 표현식이 다음과 같다,

        <aside>
        ✅ x.clone() != x `[서로 다른 메모리 주소]`
        .x.clone().getClass() == x.getClass() `[같은 클래스]`사실이지만 이것이 절대적인 요구 사항은 아니다. 일반적으로 다음과 같은 경우를 보자면x.clone().equals(x)이것은 사실이지만 절대적인 요구 사항은 아니다.   관례에 따라 반환된 객체는 super.clone을 호출하여 얻어야 합니다.  클래스와 모든 상위 클래스(최상위 Object class 제외)가 이 규칙을 따르는 경우 x.clone().getClass() == x.getClass()가 된다.

        </aside>

    - 규칙에 따라 이 메서드가 반환하는 개체는 이 개체(복제 중인 개체)와 독립적이어야 합니다. 이러한 독립성을 달성하려면 객체를 반환하기 전에 super.clone에 의해 반환된 객체의 하나 이상의 필드를 수정해야 할 수 있습니다.
    - 일반적으로 이것은 복제되는 개체의 내부 "심층 구조"를 구성하는 변경 가능한 개체를 복사하고 이러한 개체에 대한 참조를 복사본에 대한 참조로 바꾸는 것을 의미한다.
    - 클래스에 변경 불가능한 객체에 대한 참조 또는 기본 필드만 포함된 경우 일반적으로 super.clone에서 반환한 객체의 필드를 수정할 필요가 없습니다.
      클래스 Object에 대한 메소드 복제는 특정 복제 작업을 수행합니다. 먼저 이 개체의 클래스가 Cloneable 인터페이스를 구현하지 않으면 CloneNotSupportedException이 throw됩니다.
    - 모든 배열은 인터페이스 Cloneable을 구현하는 것으로 간주되며 배열 유형 T[]의 복제 메서드 반환 유형은 T[]이며 여기서 T는 참조 또는 기본 유형입니다. 그렇지 않으면 이 메서드는 이 개체의 클래스에 대한 새 인스턴스를 만들고 할당에 의한 것처럼 이 개체의 해당 필드 내용을 정확히 사용하여 모든 필드를 초기화한다.
    - `필드의 내용 자체는 복제되지 않습니다. 따라서 이 메서드는 "깊은 복사" 작업이 아니라 이 개체의 "얕은 복사"를 수행한다.`Object 클래스는 자체적으로 Cloneable 인터페이스를 구현하지 않으므로 클래스가 Object인 객체에서 복제 메서드를 호출하면 런타임에 예외가 발생합니다.

  > CloneNotSupportedException에 대해서 검사 예외(Checked Exception)가 아닌 비검사 예외(Unchecked Exception) 였어야 한다 라고 얘기함.
  >
    - checked , Uncheckd Exception
        - checked Exception
            - 프로그램 외부에서 발생할 수 있는 오류.

          > For example, the constructor of*[FileInputStream](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/FileInputStream.html#%3Cinit%3E(java.io.File))*throws*FileNotFoundException*ifthe input file does not exist.
          >
          >
          > **Java verifies checked exceptions at compile-time.**
          >
          > Therefore, we should use the*[throws](https://www.baeldung.com/java-throw-throws)*keyword to declare a checked exception:
        >
        - unchecked Exception
            - 프로그램 logic 내부의 대해 발생할 수 있는 오류.

          > For example, if we divide a number by 0, Java will throw ArithmeticException:
        
### 📌clone 재정의시 주의사항

- clone은 원본 객체에 아무런 해를 끼치지 않는 동시에 복제된 객체의 불변식을 보장해야한다.
- 복제할 수 있는 클래스를 만들기 위해 일부 필드에서 final 한정자를 제거해야 할 수도 있다.
- 재정의될 수 있는 메소드를 호출하지 않아야한다.

## How

- clone 재정의 예시

    ```java
    class A implements Cloneable {
    	int num;
    
    	public A() {
    		System.out.println("---------------------");
    		System.out.println("A constructor");
    		System.out.println("---------------------");
    	}
    
    	public A(int num) {
    		System.out.println("---------------------");
    		System.out.println("A constructor");
    		System.out.println("---------------------");
    		this.num = num;
    	}
    
    	@Override
    	public A clone() {
    		try {
    			System.out.println("---------------------");
    			System.out.println("A Clone");
    			System.out.println("---------------------");
    			return (A)super.clone();
    		} catch (CloneNotSupportedException e) {
    			throw new RuntimeException();
    		}
    	}
    
    	@Override
    	public String toString() {
    		return "A{" +
    				"num=" + num +
    				'}';
    	}
    }
    ```

  Object의 clone 메소드는 Object를 반환하지만 위의 코드에선 A를 반환하게 했다. 이와 같은 일이 가능한 이유는 자바가 공변 반환 타이핑(covariant return typing) 을 지원하기 때문이다.

  A 클래스의 clone메소드는 super.clone을 호출하여 A 클래스로 캐스팅하고 반환한다. 이때 super.clone은 Object의 clone을 호출하는데 Object의 clone은 native 메소드로 Foo 클래스의 각 필드를 기준으로 생성자를 호출하지 않고 객체를 복사한다. 각 필드를 '=' 을 이용해서 복사한다고 생각하면 쉽게 이해할 수 있을 것 같다.

  중요한 점은 여기서 등장한다.‼️만약 클래스의 `필드가 가변 객체(래퍼런스)를 참조`하는 필드일 때 단순하게 super.clone만 반환하면 큰일이 날 수 있다는 것이다.

- 가변 객체 필드 일 때 Clone 예시

    ```java
    package com.programmers.java.chapt3.item13;
    
    import java.util.Arrays;
    
    public class Stack implements Cloneable {
    	private A[] elements;
    	private int size = 0;
    	private static final int DEFAULT_INITIAL_CAPACITY = 16;
    
    	public Stack() {
    		System.out.println("---------------");
    		System.out.println("Stack constructor");
    		this.elements = new A[DEFAULT_INITIAL_CAPACITY];
    	}
    
    	public A[] getElements() {
    		return elements;
    	}
    
    	public int getSize() {
    		return size;
    	}
    
    	public void push(A e) {
    		ensureCapacity();
    		elements[size++] = e;
    	}
    
    	public A pop() {
    		if(size == 0) {
    			throw new EmptyStackException();
    		}
    		A result = elements[--size];
    		elements[size] = null;
    		return result;
    	}
    
    	private void ensureCapacity() {
    		if(elements.length == size) {
    			elements = Arrays.copyOf(elements,2 * size + 1);
    			System.out.println(elements.length);
    		}
    	}
    
    	@Override
    	public String toString() {
    		StringBuilder stringBuilder = new StringBuilder();
    		int i = 0;
    		while (elements[i] != null) {
    			stringBuilder.append(elements[i].num);
    			stringBuilder.append(",");
    			i++;
    		}
    
    		return stringBuilder.toString();
    	}
    
    	@Override
    	public Stack clone() {
    		try {
    			return (Stack) super.clone();
    		} catch (CloneNotSupportedException e) {
    			throw new AssertionError();
    		}
    	}
    }
    ```

    - 전반적인 구조

      Stack 클래스는 A 클래스의 배열을 필드로 가지고있는 클래스이다. clone 메소드를 재정의 하기위해 Cloneable을 상속받았았고 재정의 한 clone 메소드는 super.clone을 반환한다.

      다음은 Stack 객체를 하나 생성하고 2개의 A 객체를 push한다. 그리고 clone으로 새로운 Stack 객체를 만든 뒤 cloneStack 객체에 1개의 객체를 push 한다. 기존의 객체와 clone한 객체의 elements는 달라야 하는게 정상이라고 생각할 수 있을 것이다.


    ### test code

    
```java
    package com.programmers.java.chapt3.item13;
    
    import static org.junit.jupiter.api.Assertions.*;
    
    import java.util.Arrays;
    
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    
    class StackTest {
    	@Test
    	@DisplayName("Stack super.clone() 반환")
    	public void cloneTest(){
    	    //given
    		Stack stack = new Stack();
    		
    		stack.push(new A(10));
    		stack.push(new A(20));
    
    		//when
    		Stack clone = stack.clone();
    		
    		clone.push(new A(30));
    		//then
    
    		Assertions.assertNotEquals(stack,clone);
    		Assertions.assertEquals(stack.getElements(),clone.getElements());
    		Assertions.assertNotEquals(stack.getSize(),clone.getSize());
    
    		System.out.println(Arrays.toString(stack.getElements()));
    		System.out.println(Arrays.toString(clone.getElements()));
    
    		/**
    		 * Note : 위와 같이 테스트는 성공한다. Stack 클래스의 int size 필드[primitive type] 는 기본타입으로 값이 정상적으로 복사되어 서로 다른 값을 갖지만,
    		 * 	참조 필드인 elements 의 경우 같은 주소를 가지게 되어 변경사항이 원본에도 영향을 끼친다.
    		 */
    	}
    }
    ```
    
    - 참조 타입의 필드를 가질 때 clone
        
        ```java
        @Override
            public Stack clone() {
                try {
                    Stack stack = (Stack) super.clone();
                    stack.elements = this.elements.clone();
                    return stack;
                } catch (CloneNotSupportedException e) {
                    throw new AssertionError();
                }
            }
```
        
  - 해당 방법에도 여전히 단점이 존재한다.
        
  - 기존 객체와 복사된 객체의 객체 배열이 서로 다른 주소를 가지고 있어도 배열의 원소가 가지고 있는 `A라는 객체`는 같은 객체이다. 이 경우 반복자를 이용해 기존 elements가 가지고 있는 원소들을 복사된 배열에 맞게 새로 생성하여 넣어주면 해결할 수 있을 것이다.
        
  - 재귀 호출을 이용한 생성자 생성 및 반복자[Hashtable]
        
      ```java
      public class HashTable implements Cloneable {
        private Entry[] buckets = ...;
        private static class Entry {
          final Object key;
          Object value;
          Entry next;
          // contstructor...
        }
        
        // 잘못된 clone 메서드 예
        @Override public HashTable clone() {
          try {
            HashTable result = (HashTable) super.clone();
            result.buckets = buckets.clone();
            return result;
          } catch (CloneNotSupportedException e) {
            throw new AssertionError();
          }
        }
      }
        
      ```
        
      - HashTable의 clone 메서드를 Stack의 clone메서드를 구현했던 것처럼 재귀적으로 clone을 호출.
      - 이 HashTable은 원본과 `같은 값을 참조하는 buckets`을 갖는다. 이런 문제의 경우 다음처럼 해결할 수 있다.
        
      ```java
      public class HashTable implements Cloneable {
        private Entry[] buckets = ...;
        private static class Entry {
          final Object key;
          Object value;
          Entry next;
          // constructor
        }
        Entry deepCopy() {
          return new Entry(key, value, next == null ? null : next.deepCopy());
        }
        @Override public HashTable clone() {
          try {
            HashTable result = (HashTable) super.clone();
            result.buckets = new Entry[buckets.length];
            for (int i = 0; i < buckets.length; i++)
              if (buckets[i] != null)
                result.buckets[i] = buckets[i].deepCopy();
            return result;
          } catch (CloneNotSupportedException e) {
            throw new AssertionError();
          }
        }
      }
        
      ```
        
      배열 내부를 정확히 복제하기 위해 `deepCopy` 라는 메서드를 새로 정의하였다. 단순히 생성자를 호출하도록 한 것이다.  
        
      - 하지만 해당 방식에도 문제점이 하나 있다. ‼️
            
          bucket의 크기가 크지 않다면 괜찮지만 너무 크다면 `콜 스택 오버플로가 발생`한다. 콜 스택 오버플로를 막기 위해서 다음과 같이 `deepCopy` 메서드를 수정하자. 알고리즘 풀이에서 흔히 사용되는 것 처럼 recursive를 iterative로 바꾸면 된다.
            
          ```java
          Entry deepCopy() {
            Entry result = new Entry(key, value, next);
            for (Entry p = result; p.next != null; p = p.next)
              p.next = new Entry(p.next.key, p.next.value, p.next.next);
            return result;
          }
            
          ```
            
        
### 그냥 정적 팩토리 메서드로 구현하세요.
        
  - 지금까지 살펴본 clone 메서드를 구현하면서 정확히 동작하길 원하기란 어렵다.(게다가, `clone 메서드는 thread safe`하지 않다.)
  - 가장 쉽고 효과적인 방법은 `복제용 생성자나 복제용 정적 팩토리 메서드`를 만드는 것이다.
  - 이 예제는 Collections 이나 Map 인터페이스에서도 이미 구현된 것을 알 수 있다.
  - 아래 코드는 Collections의 copy 정적 메서드의 내부 구현이다.
  - 원본과 List에서 원소를 가져와 그대로 복제 대상에 넣는 것을 알 수 있다.
  
  ```java
       public static <T> void copy(List<? super T> dest, List<? extends T> src) {
           int srcSize = src.size();
           if (srcSize > dest.size())
               throw new IndexOutOfBoundsException("Source does not fit in dest");
           if (srcSize < COPY_THRESHOLD ||
               (src instanceof RandomAccess && dest instanceof RandomAccess)) {
               for (int i=0; i<srcSize; i++)
                   dest.set(i, src.get(i));
           } else {
               ListIterator<? super T> di=dest.listIterator();
               ListIterator<? extends T> si=src.listIterator();
               for (int i=0; i<srcSize; i++) {
                   di.next();
                   di.set(si.next());
               }
           }
       }
    
   ```



## Why

<aside>
✏️ 필드의 내용 자체는 복제되지 않을 수 있다.(래퍼런스 타입) 따라서 cloen 메서드는 `"깊은 복사" 작업이 아니라 이 개체의 "얕은 복사"를 수행 할 수 있음에 유의`한다. 그에 대한 파급렵으로는 NPE 발생 또는 프로그램이 오작동 할 수 있는 여지가 발생할 수 있기 때문이다.

</aside>


참조
- [ref1](https://github.com/Meet-Coder-Study/book-effective-java/commit/97028875917afd89929a805714a2c0dc42057670)
- [ref2](https://www.baeldung.com/java-checked-unchecked-exceptions)