### 스프링 부트와 JPA 활용 1 - 웹 애플리케이션 개발

#### 섹션 2. 도메인 분석 설계
1. 연관관계의 주인
  - JPA에서는 연관관계의 주인이 중요!
  - DB에서 외래키(FK)를 가지는 쪽이 연관관계의 주인으로 사용하는 것을 권장!
  - Ex) 주문(Order)과 주문상품(OrderItem) 연관관계 
      - OrderItem에서 FK를 가지고 있기에 주인!
         ```java
	 // OrderItem가 주인이기에 JoinColumn으로 연결

	 @ManyToOne
	 @JoinColumn(name = "item_id")
	 private Item item;
	 ```
      - Order는 연관관계의 거울이기에 mappedBy 작성!
      	 ```java
	 @OneToMany(mappedBy = "orderItem")
	 private List<OrderItem> orderItems = new ArrayList<>();
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
