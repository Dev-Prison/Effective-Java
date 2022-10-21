# Effective Java 이펙티브 자바

## ☕️ 스터디 규칙
- 발표순서 : **이연우** - 김수미 - 이용훈 - 김형욱 - **김병연**
- 스크럼마스터 : **이용훈** - 김병연 - 김수미 - 이연우 - 김형욱
- 스터디 **전날 오후 8시 전까지** 발표자는 발표자료를 Github에 업로드 해야 합니다. (지각시 커피 +1)
- 발표자 외 스터디 시간 전까지 comment를 통해 주제마다 질문을 한개씩 등록해야 합니다. (지각시 커피 +1)
  - comment를 달 때, `Files changed` → `Review Changes` 에서 `approve` 도 해주세요.
- 발표자가 발표자료를 늦게 업로드 해서 다른 팀원들이 comment를 달지 못했다? 발표자가 연대책임 합니다. (커피 +4)
<br>

## ⚙️ Git 사용 방식
- 스터디원 각자 본인만의 branch를 생성하여 해당 branch 에서 작업(코드/md 작성)합니다.
- branch에서의 작업이 완료되었으면 해당 작업에 대한 PR을 날려, 스터디가 끝나고 merge 할 수 있도록 해주세요.
- main branch의 `Readme.md` 에는 Item 별로 작성한 발표자료의 링크를 모아주세요. (아래 예시 참고)
- 발표자료가 저장되어있는 링크에는 발표자료 외에 발표에 참고할 수 있는 코드 등을 저장해두셔도 됩니다.
<br>

## 📌 스크럼 마스터가 해야할 일
- 발표자 준비 자료 확인
- 발표 전 `approve` 확인
- 발표자 자료 외 merge 안된 PR merge 하도록 요청하기
- main branch 최신화 확인
- 발표 순서, 스마 Bold 처리 후 README 업데이트

<br>

## 🍄 Item List

> **1장 : 들어가기** <br>

> **2장 : 객체 생성과 파괴** <br>

- Item1 : 생성자 대신 정적 팩토리 메서드를 고려하라
- [Item2 : 생성자에 매개변수가 많다면 빌더를 고려하자](https://github.com/YHLEE9753/ReadingRecord/blob/master/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94/Chapter2_%EA%B0%9D%EC%B2%B4%EC%83%9D%EC%84%B1%EA%B3%BC%ED%8C%8C%EA%B4%B4/Item2_%EC%83%9D%EC%84%B1%EC%9E%90%EC%97%90_%EB%A7%A4%EA%B0%9C%EB%B3%80%EC%88%98%EA%B0%80_%EB%A7%8E%EB%8B%A4%EB%A9%B4_%EB%B9%8C%EB%8D%94%EB%A5%BC_%EA%B3%A0%EB%A0%A4%ED%95%98%EC%9E%90.md)
- [Item3 : private 생성자 혹은 enum 타입으로 싱글톤임을 보증하라](https://cat-tungsten-c56.notion.site/DAY1-3-eb593dad6c9941b0b4c787ed5e6be3bf)
- [Item4 : 인스턴스화를 막으려거든 private 생성자를 사용하라](https://cat-tungsten-c56.notion.site/DAY2-4-3f1ba602a5604354b7980f05b28a1996)
- [Item5 : 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라](https://cat-tungsten-c56.notion.site/DAY2-5-e7d4fc8086374b4eb0cf17a47f796ce4)
- Item6 : 불필요한 객체 생성을 피하라
- [Item7 : 다 쓴 객체 참조를 해제하라](https://cat-tungsten-c56.notion.site/DAY3-7-5606f48a97ac45caa62958a27893f081)
- Item8 : finalizer와 cleaner 사용을 피하라
- [Item9 : try-finally보다는 try-with-resources를 사용하라](https://cat-tungsten-c56.notion.site/DAY4-9-512102eb72db408aa6ef5e4b7a32d9bf)

> **3장 : 모든 객체의 공통 메서드** <br>

- [Item10 : equals는 일반 규약을 지켜 재정의하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt3/item10/item10_%EB%B0%9C%ED%91%9C.md)
- [Item11 : equals를 재정의하려거든 hashCode도 재정의하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt3/item11/Item11_equals%EB%A5%BC_%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%98%EB%A0%A4%EA%B1%B0%EB%93%A0_hashCode%EB%8F%84_%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%98%EB%9D%BC.md)
- [Item12 : toString을 재정의하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt3/item12/Item12_toString.md)
- [Item13 : clone 재정의는 주의해서 진행하라](https://github.com/Dev-Prison/Effective-Java/tree/main/app/src/main/java/com/programmers/java/chapt3/item13)
- [Item14 : Comparable을 구현할지 고려하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt3/item14/Item14_compareTo.md)

> **4장 : 클래스와 인터페이스** <br>

- [Item15 : 클래스와 멤버의 접근 권한을 최소화하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt4/item15/item15_%ED%81%B4%EB%9E%98%EC%8A%A4%EC%99%80_%EB%A9%A4%EB%B2%84%EC%9D%98_%EC%A0%91%EA%B7%BC%EA%B6%8C%ED%95%9C%EC%9D%84_%EC%B5%9C%EC%86%8C%ED%99%94_%ED%95%98%EB%9D%BC_%EB%B0%9C%ED%91%9C.md)
- [Item16 : public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt4/item16/Item16_public%ED%81%B4%EB%9E%98%EC%8A%A4%EC%97%90%EC%84%9C%EB%8A%94_public%ED%95%84%EB%93%9C%EA%B0%80_%EC%95%84%EB%8B%8C_%EC%A0%91%EA%B7%BC%EC%9E%90%EB%A9%94%EC%84%9C%EB%93%9C%EB%A5%BC_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- [Item17 : 변경 가능성을 최소화하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt4/item17/Item17_%EB%B3%80%EA%B2%BD_%EA%B0%80%EB%8A%A5%EC%84%B1%EC%9D%84_%EC%B5%9C%EC%86%8C%ED%99%94%ED%95%98%EB%9D%BC.md)
- [Item18 : 상속보다는 컴포지션을 사용하라](https://yeonees.notion.site/item-18-6b82a4bd1d7647cba555f800082a5581)
- [Item19 : 상속을 고려해 설계하고 문서화하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt4/item19/Item19.md)
- [Item20 : 추상 클래스보다는 인터페이스를 우선하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt4/item20/Item20_%EC%B6%94%EC%83%81%ED%81%B4%EB%9E%98%EC%8A%A4%EB%B3%B4%EB%8B%A4%EB%8A%94_%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4.md)
- [Item21 : 인터페이스는 구현하는 쪽을 생각해 설계하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt4/item21/Item21_%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4%EB%8A%94_%EA%B5%AC%ED%98%84%ED%95%98%EB%8A%94_%EC%AA%BD%EC%9D%84_%EC%83%9D%EA%B0%81%ED%95%B4_%EC%84%A4%EA%B3%84%ED%95%98%EB%9D%BC.md)
- [Item22 : 인터페이스는 타입을 정의하는 용도로만 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt4/item22/item22_%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4%EB%8A%94_%ED%83%80%EC%9E%85%EC%9D%84_%EC%A0%95%EC%9D%98%ED%95%98%EB%8A%94_%EC%9A%A9%EB%8F%84%EB%A1%9C%EB%A7%8C_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- [Item23 : 태그 달린 클래스보다는 클래스 계층구조를 활용하라](app/src/main/java/com/programmers/java/chapt4/item23/README.md)
- [Item24 : 멤버 클래스는 되도록 static 으로 만들라](app/src/main/java/com/programmers/java/chapt4/item24/item24.md)
- [Item25 : 톱레벨 클래스는 한 파일에 하나만 담으라](app/src/main/java/com/programmers/java/chapt4/item25/item25_톱레벨_클래스는_한파일에_하나만_담으라.md)

> **5장 : 제네릭** <br>

- [Item26 : 로 타입은 사용하지 말라](app/src/main/java/com/programmers/java/chapt5/item26/Item26_로_타입은_사용하지_말라.md)
- [Item27 : 비검사 경고를 제거하라](app/src/main/java/com/programmers/java/chapt5/item27/비검사_경고를_제거하라.md)
- [Item28 : 배열보다는 리스트를 사용하라](https://yeonees.notion.site/item-28-bcd551fd107648e98794d92be3d3ef5b)
- [Item29 : 이왕이면 제네릭 타입으로 만들라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt5/item29/item29.md)
- [Item30 : 이왕이면 제네릭 메서드로 만들라](https://github.com/Dev-Prison/Effective-Java/blob/ynoolee/app/src/main/java/com/programmers/java/chapt5/item30/item30_%EC%9D%B4%EC%99%95%EC%9D%B4%EB%A9%B4_%EC%A0%9C%EB%84%A4%EB%A6%AD_%EB%A9%94%EC%84%9C%EB%93%9C%EB%A1%9C_%EB%A7%8C%EB%93%A4%EB%9D%BC.md)
- [Item31 : 한정적 와일드카드를 사용해 API 유연성을 높이라](https://github.com/Dev-Prison/Effective-Java/blob/0e660de3fd673b636cce15fcf49fa4d19ea106e3/app/src/main/java/com/programmers/java/chapt5/item31/Item31%20-%ED%95%9C%EC%A0%95%EC%A0%81%20%EC%99%80%EC%9D%BC%EB%93%9C%EC%B9%B4%EB%93%9C%EB%A5%BC%20%EC%82%AC%EC%9A%A9%ED%95%B4%20API%20%EC%9C%A0%EC%97%B0%EC%84%B1%EC%9D%84%20%EB%86%92%EC%9D%B4%EB%9D%BC.md)
- [Item32 : 제네릭과 가변인수를 함께 쓸 때는 신중하라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item32.-%EC%A0%9C%EB%84%A4%EB%A6%AD%EA%B3%BC-%EA%B0%80%EB%B3%80%EC%9D%B8%EC%88%98%EB%A5%BC-%ED%95%A8-%EC%93%B8-%EB%95%8C%EB%8A%94-%EC%8B%A0%EC%A4%91%ED%95%98%EB%9D%BC)
- [Item33 : 타입안정 이종 컨테이너를 고려하라](https://yeonees.notion.site/item-33-009fd935813c4247a028f34a5ba522b0)

> **6장 : 열거 타입과 애너테이션** <br>

- [Item34 : int 상수 대신 열거 타입을 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt6/item34.md)
- [Item35 : ordinal 메소드 대신 인스턴스 필드를 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt6/item35/item35_ordinal_%EB%A9%94%EC%86%8C%EB%93%9C_%EB%8C%80%EC%8B%A0_%EC%9D%B8%EC%8A%A4%ED%84%B4%EC%8A%A4_%ED%95%84%EB%93%9C%EB%A5%BC_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- [Item36 : 비트 필드 대신 EnumSet을 사용하라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item36.-%EB%B9%84%ED%8A%B8-%ED%95%84%EB%93%9C-%EB%8C%80%EC%8B%A0-EnumSet%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC)
- [Item37 : EnumMap 을 사용하라](https://morning-paprika-8fa.notion.site/Item37-EnumMap-54f6685b29d24a5bba0d749770bb2aa2)
- [Item38 : 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라.](https://yeonees.notion.site/item-38-e1f94d06a0cc4d93990c273d0de71b99)
- [Item39 : 명명 패턴보다 Annotation을 사용하라](https://cat-tungsten-c56.notion.site/DAY12-Annotation-68f703aa60174f4e80e05c5399997745)
- [Item40 : 어노테이션을 일관되게 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt6/item40/Override_%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98%EC%9D%84_%EC%9D%BC%EA%B4%80%EB%90%98%EA%B2%8C_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- [Item41 : 정의하려는 것이 타입이라면 마커 인터페이스를 사용하라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item41.-%EC%A0%95%EC%9D%98%ED%95%98%EB%A0%A4%EB%8A%94-%EA%B2%83%EC%9D%B4-%ED%83%80%EC%9E%85%EC%9D%B4%EB%9D%BC%EB%A9%B4-%EB%A7%88%EC%BB%A4-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC)

> **7장 : 람다와 스트림** <br>

- [Item42 : 익명 클래스보다 람다를 사용하라](https://morning-paprika-8fa.notion.site/Item42-badd9f20079c4286947ed7843eca011f)
- [Item43 : 람다보다는 메서드 참조를 사용하라](https://yeonees.notion.site/Item-43-380ddf5f9cac4005954f11ffd966f72b)
- [Item44 : 표준 함수형 인터페이스를 사용하라](https://cat-tungsten-c56.notion.site/DAY13-feat-09f5b930d8214104846a69b2d71174d6)
- [Item45 : 스트림은 주의해서 사용하라](app/src/main/java/com/programmers/java/chapt7/item45/스트림은_주의해서_사용하라.md)
- [Item46 : 스트림에서는 부작용 없는 함수를 사용하라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item46.-%EC%8A%A4%ED%8A%B8%EB%A6%BC%EC%97%90%EC%84%9C%EB%8A%94-%EB%B6%80%EC%9E%91%EC%9A%A9-%EC%97%86%EB%8A%94-%ED%95%A8%EC%88%98%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC-baa2kzb7)
- [Item47 : 반환 타입으로는 스트림보다 컬렉션이 낫다](https://morning-paprika-8fa.notion.site/Item47-12ee150aad9743da8c366622649961bd)
- [Item48 : 스트림 병렬화는 주의해서 사용하라](https://yeonees.notion.site/Item-48-bf444377089449268b013753112ca4c1)

> **8장 : 열거 타입과 애너테이션** <br>

- [Item49 : 매개변수가 유효한지 검사하라](https://cat-tungsten-c56.notion.site/DAY14-ce4b6cbe3a8c434c859e5b24bb51e943)
- [Item50 : 적시에 방어적 복사본을 만들라](https://ultra-stream-0a3.notion.site/Item-50-2f18dc7cc6fc49ac8148b258ab5905b3)
- [Item51 : 메서드 시그니처를 신중히 설계하라](https://github.com/YHLEE9753/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt8/item51/item51.md)
- [*(형욱)Item52 : 다중정의는 신중히 사용하라](https://www.notion.so/Item52-6e891e60b11740cc944920225e329da2)
- [Item53 : 가변인수는 신중히 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt8/item53/%EA%B0%80%EB%B3%80%EC%9D%B8%EC%88%98%EB%8A%94_%EC%8B%A0%EC%A4%91%ED%9E%88_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- [Item54 : null 이 아닌, 빈 컬렉션이나 배열을 반환하라](https://cat-tungsten-c56.notion.site/DAY15-null-325f21f2bf1341a6b4b491fa725fb951)
- [Item55 : 옵셔널 반환은 신충히 해라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item55.-%EC%98%B5%EC%85%94%EB%84%90-%EB%B0%98%ED%99%98%EC%9D%80-%EC%8B%A0%EC%A4%91%ED%9E%88-%ED%95%98%EB%9D%BC)
- [Item56 : 항상 문서화 주석을 작성하라](https://morning-paprika-8fa.notion.site/Item56-API-e703f9682112416db7486bc366e485c3)

> **9장 : 일반적인 프로그래밍** <br>
- [Item57 : 지역변수의 범위를 최소화하라](https://yeonees.notion.site/Item-57-5baaf390b4fd4c84ba1e0236a183eee2)
- [Item58 : 전통적 for 문 보다는 for-each 문을 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt9/item58/%EC%A0%84%ED%86%B5%EC%A0%81_for%EB%AC%B8_%EB%B3%B4%EB%8B%A4%EB%8A%94_for-each_%EB%AC%B8%EC%9D%84_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- [Item59 : 라이브러리를 익히고 사용하라](https://cat-tungsten-c56.notion.site/DAY16-3e931f4c73804aa08d99b085c42c4008)
- [Item60 : 정확한 답이 필요하다면 float와 double은 피하라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item60.-%EC%A0%95%ED%99%95%ED%95%9C-%EB%8B%B5%EC%9D%B4-%ED%95%84%EC%9A%94%ED%95%98%EB%8B%A4%EB%A9%B4-float%EC%99%80-double%EC%9D%80-%ED%94%BC%ED%95%98%EB%9D%BC)
- [Item61 : 박싱된 기본 타입보다는 기본 타입을 사용하라](https://morning-paprika-8fa.notion.site/Item61-53a128ba88cf493e84a896a46a7d3596)
- [Item62 : 다른 타입이 적절하다면 문자열 사용을 피하라](https://yeonees.notion.site/item-62-05800a7431a14a7b9b8585c1a32561c5)
- [Item63 : 문자열 연결은 느리니 주의하라](https://morning-paprika-8fa.notion.site/Item63-71e48a50e595469fa06faa59d97d4313)
- [Item64 : 객체는 인터페이스를 사용해 참조하라](https://cat-tungsten-c56.notion.site/DAY17-c2e74e8631d249b8be380c986b50e8f2)
- [Item65 : 리플렉션보다는 인터페이스를 사용하라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item65.-%EB%A6%AC%ED%94%8C%EB%A0%89%EC%85%98%EB%B3%B4%EB%8B%A4%EB%8A%94-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC)
- [Item66 : 네이티브 메소드는 신중히 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt9/item66/item66_%EB%84%A4%EC%9D%B4%ED%8B%B0%EB%B8%8C%EB%A9%94%EC%84%9C%EB%93%9C%EB%8A%94_%EC%8B%A0%EC%A4%91%ED%9E%88_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- [Item67 : 최적화는 신중히 하라](https://yeonees.notion.site/item-67-0323496d0b6b43fb8ec2f6e8d45b6b61)
- [Item68 : 일반적으로 통용되는 명명규칙을 따르라](app/src/main/java/com/programmers/java/chapt9/item68/item68_일반적으로_통용되는_명명규칙을_따르라.md)

> **10장 : 예외** <br>
- [Item69 : 예외는 진짜 예외 상황에만 사용하라](https://cat-tungsten-c56.notion.site/DAY18-c510100149e14c9ea7e0f4302ba426e4)
- [Item70 : 복구할 수 있는 상황에는 검사 예외를, 프로그래밍 오류에는 런타임 예외를 사용하라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item70.-%EB%B3%B5%EA%B5%AC%ED%95%A0-%EC%88%98-%EB%8A%94-%EC%83%81%ED%99%A9%EC%97%90%EB%8A%94-%EA%B2%80%EC%82%AC-%EC%98%88%EC%99%B8%EB%A5%BC-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EC%98%A4%EB%A5%98%EC%97%90%EB%8A%94-%EB%9F%B0%ED%83%80%EC%9E%84-%EC%98%88%EC%99%B8%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC)
- [Item71 : 필요없는 예외 검사는 피하라](https://morning-paprika-8fa.notion.site/Item71-0b78fe39d3714bc4a204639aeecbb4d0)
- [Item72 : 표준 예외를 사용하라](https://yeonees.notion.site/Item72-598f9e43666b4558af78df55804ba1a8)
- [*(연우)Item73 : 추상화 수준에 맞는 예외를 던져라](https://ultra-stream-0a3.notion.site/Item-73-5f7b366c6c594774ae2b762623c6c7b0)
- [Item74 : 메서드가 던지는 모든 예외를 문서화하라](https://cat-tungsten-c56.notion.site/DAY-19-a90461233833458ea812fe2666e16793)
- [Item75 : 예외의 상세 메시지에 실패 관련 정보를 담으라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item75.-%EC%98%88%EC%99%B8%EC%9D%98-%EC%83%81%EC%84%B8-%EB%A9%94%EC%8B%9C%EC%A7%80%EC%97%90-%EC%8B%A4%ED%8C%A8-%EA%B4%80%EB%A0%A8-%EC%A0%95%EB%B3%B4%EB%A5%BC-%EB%8B%B4%EC%9C%BC%EB%9D%BC)
- [Item76 : 가능한 한 실패 원자적으로 만들라](https://morning-paprika-8fa.notion.site/Item76-4fee02ecc4414426882e3f30406babf8)
- [Item77 : 예외를 무시하지 말라](https://yeonees.notion.site/item77-6b2b12f1d91f433491388e3547031926)

> **11장 : 동시성** <br>
- [Item78 : 공유 중인 가변 데이터는 동기화해 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/ynoolee/app/src/main/java/com/programmers/java/chapt11/item78/item78_%EA%B3%B5%EC%9C%A0_%EC%A4%91%EC%9D%B8_%EA%B0%80%EB%B3%80_%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%8A%94_%EB%8F%99%EA%B8%B0%ED%99%94_%ED%95%B4_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
