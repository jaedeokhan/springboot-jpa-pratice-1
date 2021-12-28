## 스프링 부트와 JPA 활용 1 - 웹 애플리케이션 개발

### 섹션 2. 도메인 분석 설계

#### 엔티티 클래스 개발1

1. 연관관계의 주인
  - JPA에서는 연관관계의 주인이 중요!
  - DB에서 외래키(FK)를 가지는 쪽이 연관관계의 주인으로 사용하는 것을 권장!
  - Ex) 주문(Order)과 주문상품(OrderItem) 연관관계 
      - OrderItem에서 FK를 가지고 있기에 주인!
```java
public class OrderItem {
	```
	
	// OrderItem이 주인이기에 JoinColumn으로 연결
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	```
}
```

```java
public class Order {

	```

	// Order는 연관관계의 거울이기에 mappedBy 작성!
	
	@OneToMany(mappedBy = "orderItem")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	```
}
```

2. 값 타입(임베디드 타입) 사용
   - Address를 임베디드 타입으로 사용!
   - Address 클래스에서는 `@Embeddable` 사용
   - Address 클래스를 사용하는 곳에서는 `@Embedded` 사용

3. Item 클래스의 싱글 테이블 전략
   - 자식 클래스인 Book, Movie, Album을 부모 클래스인 Item의 dtype 컬럼을 이용한 싱글 테이블 전략
   - Item 클래스에서 두가지 작성 
      - `@Inheritance(strategy = InheritanceType.SINGLE_TYPE)` 사용
      - `@DiscriminatorColumn(name = "dtype")` 
   - 자식 클래스에서 dtype 사용
      - `Book` 클래스  : `@DiscriminatorValue("B")`
      - `Album` 클래스 : `@DiscriminatorValue("A")`
      - `Movie` 클래스 : `@DiscriminatorValue("M")`

4. Enum 타입의 사용
   - Enum 타입을 사용하는 클래스에서 `@Enumerated(EnumType.STRING)` 어노테이션 사용
      - @Enumerated(EnumType.ORDINAL)을 지양해야하는 이유는?
         - ORDINAL은 값이 0, 1과 같이 숫자로 저장이 되서 중간에 요구사항이 추가되면 망한다....ㅋㅋㅋㅋ
	 - STRING을 사용해서 새로운 요구사항이 들어와도 오류없이 사용가능하도록 할 것!


#### 엔티티 클래스 개발2

1. @ManyToMany 실무에서는?
   - @ManyToMany 실무에서는 권장하지 않는다. 매핑 테이블에 추가되는 컬럼들도 많고 실무에서 요구사항은 복잡하고 추가되는 것이 많기 때문!!

2. @Setter 지양
   - 조회성은 많기에 @Getter는 사용해도 되지만, 무분별한 @Setter는 유지 보수성을 떨어트린다. @Setter 사용을 지양하고, 변경이 필요하면 변경 메서드를 만들어서 해결!

3. 값 타입은 변경 불가능하게 설계
   - 위와 같이 @Setter는 제거! 
   - JPA 스펙상 엔티티나 임베디드 타입은 기본 생성자를 public 또는 protected로 설정!
      - public 보다는 protected로 조금 더 안전하게 사용!

#### 엔티티 설계시 주의점

1. @Setter 지양
   - 변경 포인트가 많아져 유지 보수가 어려워 진다.

2. 모든 연관계는 즉시(EAGER) X, 지연(LAZY) 로딩으로 할 것!
   - EAGER로 하면 연관되어 있는 모든 쿼리가 다 불러와져서 추후에 JPQL 사용시 N + 1 문제가 발생!
   - 연관된 엔티티를 조회해야하면 fetch join 또는 엔티티 그래프 기능사용
   - @XToOne(OneToOne, ManyToOne)의 FetchType의 기본 값이 EAGER로 되어 있어 모두 LAZY로 수정!

3. 컬렉션은 필드에서 초기화
   - NPE에서 안전

4. 테이블, 컬럼명 생성 전략
   - 따로 설정해주지 않으면 엔티티(필드)는 카멜 케이스(MemberId) -> 테이블(컬럼)은 스네이크 케이스(member_id)로 설정
