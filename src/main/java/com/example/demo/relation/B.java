package com.example.demo.relation;

import jakarta.persistence.*;

// N
// 다측에 외래키가 생긴다.
@Entity
public class B {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String a01;
    private String a02;

    @ManyToOne
    // 외래키 속성 이름 지정
    @JoinColumn(name = "a.idx" )
    private A a;

}
