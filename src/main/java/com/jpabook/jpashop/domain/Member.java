package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입
    private Address address;

    @OneToMany(mappedBy = "member") // 하나의 회원이 여러개의 주문, 연관관계의 거울 ( == mappedBy)
    private List<Order> orders = new ArrayList<>();
}
