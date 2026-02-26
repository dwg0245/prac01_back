package com.example.demo.relation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// 1
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    // @ManyToMany

    // 스프링 - 관계의 주인이 누구인지 지정
    // FetchType 기본 값이 LAZY : 연관 관계의 엔티티의 값을 값이 사용 될때 SQl을 한 번 더 실행해서 가져온다.
    //                  EAGER : 현재 엔티티를 조회하면서 연관 관계의 엔티티의 값도 같이 가져온다. (나중에 n+1로 해결을 할거다. 사용하지 말아라)
    // fetch =  FetchType.LAZY 가 기본으로 되어 있음
    /* fetch = FetchType.EAGER */
    @OneToMany(mappedBy = "a", fetch =  FetchType.LAZY) // 외래키 속성 지정
    private List<B> bList;

}
