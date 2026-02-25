package com.example.demo.relation.model;

import jakarta.persistence.*;
import lombok.Getter;

// N
// 다측에 외래키가 생긴다.
@Entity
@Getter
public class B {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String b01;
    private String b02;

    // fetch =  FetchType.EAGER 가 기본으로 되어 있음
    @ManyToOne(fetch =  FetchType.LAZY)
    // 외래키 속성 이름 지정
    @JoinColumn(name = "a.idx" )
    private A a;

}
