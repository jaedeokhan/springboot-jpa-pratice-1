package com.jpabook.jpashop.domain.order;

import com.jpabook.jpashop.domain.delivery.Delivery;
import com.jpabook.jpashop.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne // 여러개의 주문을 하나의 회원이
    @JoinColumn(name = "member_id") // join을 무엇으로 할 것인지
    private Member member;

    @OneToMany(mappedBy = "orderItem")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간, Java8 부터는 Date를 사용하면서 @(어노테이션)을 붙이지 않고 LocalDateTime으로 사용가능

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 ( ORDER, CANCEL)
}
