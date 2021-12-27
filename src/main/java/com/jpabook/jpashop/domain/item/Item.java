package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Book, Movie, Album 객체들을 하나의 Item 테이블에 dtype 컬럼으로 구분하는 싱글 테이블 전략
@DiscriminatorColumn(name = "dtype") // 구분
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
