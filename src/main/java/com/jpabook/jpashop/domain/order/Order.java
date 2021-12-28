package com.jpabook.jpashop.domain.order;

import com.jpabook.jpashop.domain.delivery.Delivery;
import com.jpabook.jpashop.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // JPGQ SELECT o FROM order o; -> SQL SELECT * FROM order n + 1 문제
    @ManyToOne(fetch = LAZY) // 여러개의 주문을 하나의 회원이
    @JoinColumn(name = "member_id") // join을 무엇으로 할 것인지
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // x to Many는 FetchType의 default가 LAZY, 반대로 Many to x는 FetchType의 default가 EAGER
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // order_date
    private LocalDateTime orderDate; // 주문시간, Java8 부터는 Date를 사용하면서 @(어노테이션)을 붙이지 않고 LocalDateTime으로 사용가능

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 ( ORDER, CANCEL)

    // == 연관관계 메서드 == //
    // 양방향일 경우, 원자적으로 묶어서 한 번에 해결하게 사용!
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


}
