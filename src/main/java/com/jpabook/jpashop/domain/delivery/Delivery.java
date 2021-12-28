package com.jpabook.jpashop.domain.delivery;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL로 사용하면 숫자가 들어간다. 중간에 값이 추가되면 망한다... 그래서 STRING으로 사용할 것!
    private DeliveryStatus status; // READY, COMP
}
