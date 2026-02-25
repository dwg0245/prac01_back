package com.example.demo.relation;

import jakarta.persistence.*;

import java.util.List;

// 1
@Entity
public class A {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String a01;

    // 일대일 (일거를 많이 쓴다.)
    // @OneToOne
    // 일대다 (굳이 많이 쓰지 않아도 된다.)
    // @OneToMany
    // 다대다 (거의 쓰지 않는다.)
    //@ManyToMany

    // 스프링 - 관계의 주인이 누구인지 지정
    @OneToMany(mappedBy = "a") // 외래키 속성 지정
    private List<B> bList;

}
