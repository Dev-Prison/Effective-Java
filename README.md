# Effective-Java

## ☕️ 스터디 규칙
- 발표순서 : 이연우 - 이용훈 - 김형욱 - 김병연 - 김수미 
- 스터디 당일 낮 12시 전까지 발표자는 발표자료를 Github에 업로드 해야 합니다. (지각시 커피 +2)
- 발표자 외 스터디 시간(오후 10시) 전까지 comment를 통해 주제마다 질문을 한개씩 등록해야 합니다. (지각시 커피 +2)
  - comment를 달 때, `Files changed` → `Review Changes` 에서 `approve` 도 해주세요.
- 발표자가 발표자료를 늦게 업로드 해서 다른 팀원들이 comment를 달지 못했다? 발표자가 연대책임 합니다. (커피 +4)
- 각 단원이 끝날 때마다 각자 한 챕터별로 간략하게 정리를 한다.(한줄에서 세줄 사이)
  - 그리고 다음날 실시간으로 룰렛을 돌려 발표자를 정한다.
<br>

## ⚙️ Git 사용 방식
- 스터디원 각자 본인만의 branch를 생성하여 해당 branch 에서 작업(코드/md 작성)합니다.
- branch에서의 작업이 완료되었으면 해당 작업에 대한 PR을 날려, 스터디가 끝나고 merge 할 수 있도록 해주세요.
- main branch의 `Readme.md` 에는 Item 별로 작성한 발표자료의 링크를 모아주세요. (아래 예시 참고)
- 발표자료가 저장되어있는 링크에는 발표자료 외에 발표에 참고할 수 있는 코드 등을 저장해두셔도 됩니다.
<br>

## 📌 마스터가 해야할 일
- 발표자 준비 자료 확인
- 발표 전 `approve` 확인
- root `readme` 연결하기
- 발표자 자료 외 merge 안된 PR merge 하도록 요청하기
- main branch 최신화 확인하기
<br>

## 🍄 Item List
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
- [Item18 : 상속보다는 컴포지션을 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt4/item18/README.md)
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
- [Item28 : 배열보다는 리스트를 사용하라](https://absorbed-cheek-029.notion.site/item28-bcd551fd107648e98794d92be3d3ef5b)
- [Item29 : 이왕이면 제네릭 타입으로 만들라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt5/item29/item29.md)
- [Item30 : 이왕이면 제네릭 메서드로 만들라](https://github.com/Dev-Prison/Effective-Java/blob/ynoolee/app/src/main/java/com/programmers/java/chapt5/item30/item30_%EC%9D%B4%EC%99%95%EC%9D%B4%EB%A9%B4_%EC%A0%9C%EB%84%A4%EB%A6%AD_%EB%A9%94%EC%84%9C%EB%93%9C%EB%A1%9C_%EB%A7%8C%EB%93%A4%EB%9D%BC.md)
- [Item31 : 한정적 와일드카드를 사용해 API 유연성을 높이라](https://github.com/Dev-Prison/Effective-Java/blob/0e660de3fd673b636cce15fcf49fa4d19ea106e3/app/src/main/java/com/programmers/java/chapt5/item31/Item31%20-%ED%95%9C%EC%A0%95%EC%A0%81%20%EC%99%80%EC%9D%BC%EB%93%9C%EC%B9%B4%EB%93%9C%EB%A5%BC%20%EC%82%AC%EC%9A%A9%ED%95%B4%20API%20%EC%9C%A0%EC%97%B0%EC%84%B1%EC%9D%84%20%EB%86%92%EC%9D%B4%EB%9D%BC.md)
- [Item32 : 제네릭과 가변인수를 함께 쓸 때는 신중하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt5/item32/Item32_%EC%A0%9C%EB%84%A4%EB%A6%AD%EA%B3%BC_%EA%B0%80%EB%B3%80%EC%9D%B8%EC%88%98%EB%A5%BC_%ED%95%A8%EA%BB%98_%EC%93%B8_%EB%95%8C%EB%8A%94_%EC%8B%A0%EC%A4%91%ED%95%98%EB%9D%BC.md)
- [Item33 : 타입안정 이종 컨테이너를 고려하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt5/item33/README.md)

> **6장 : 열거 타입과 애너테이션** <br>

- [Item34 : int 상수 대신 열거 타입을 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt6/item34.md)
- [Item35 : ordinal 메소드 대신 인스턴스 필드를 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt6/item35/item35_ordinal_%EB%A9%94%EC%86%8C%EB%93%9C_%EB%8C%80%EC%8B%A0_%EC%9D%B8%EC%8A%A4%ED%84%B4%EC%8A%A4_%ED%95%84%EB%93%9C%EB%A5%BC_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- [Item36 : ordinal 메소드 대신 인스턴스 필드를 사용하라](app/src/main/java/com/programmers/java/chapt6/item36/Item36_비트_필드_대신_EnumSet을_사용하라.md)
- [Item37 : EnumMap 을 사용하라](app/src/main/java/com/programmers/java/chapt6/item37/item37_EnumMap을_사용하라.md)
- [Item38 : 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라.](app/src/main/java/com/programmers/java/chapt6/item38/README.md)
- [Item39 : 명명 패턴보다 Annotation을 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt6/item39.md)
- [Item40 : 어노테이션을 일관되게 사용하라](https://github.com/Dev-Prison/Effective-Java/blob/main/app/src/main/java/com/programmers/java/chapt6/item40/Override_%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98%EC%9D%84_%EC%9D%BC%EA%B4%80%EB%90%98%EA%B2%8C_%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- [Item41 : 정의하려는 것이 타입이라면 마커 인터페이스를 사용하라](https://velog.io/@yhlee9753/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C%EC%9E%90%EB%B0%94-item41.-%EC%A0%95%EC%9D%98%ED%95%98%EB%A0%A4%EB%8A%94-%EA%B2%83%EC%9D%B4-%ED%83%80%EC%9E%85%EC%9D%B4%EB%9D%BC%EB%A9%B4-%EB%A7%88%EC%BB%A4-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC)
- [Item42 : 익명 클래스보다 람다를 사용하라](https://morning-paprika-8fa.notion.site/Item42-badd9f20079c4286947ed7843eca011f)
- [Item43 : 람다보다는 메서드 참조를 사용하라](https://absorbed-cheek-029.notion.site/Item-43-380ddf5f9cac4005954f11ffd966f72b)
- [Item44 : 표준 함수형 인터페이스를 사용하라](https://www.notion.so/DAY13-feat-09f5b930d8214104846a69b2d71174d6)
- [Item45 : 스트림은 주의해서 사용하라](app/src/main/java/com/programmers/java/chapt7/item45/스트림은_주의해서_사용하라.md)
- [Item46 : 스트림에서는 부작용 없는 함수를 사용하라](app/src/main/java/com/programmers/java/chapt7/item46/Item46.md)
- [Item47 : 반환 타입으로는 스트림보다 컬렉션이 낫다](app/src/main/java/com/programmers/java/chapt7/item47/Item47.md)
- [Item48 : 스트림 병렬화는 주의해서 사용하라](app/src/main/java/com/programmers/java/chapt7/item48/README.md)
- [Item49 : 매개변수가 유효한지 검사하라](app/src/main/java/com/programmers/java/chapt7/item49/item49.md)
