package com.example.demo.common.model;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
// 각각의 엔티티로 처리하기 보다는 상속을 해서 사용한다.

@MappedSuperclass // 상속 받아간 변수도 DB 테이블에 적용
// 상속 받아간 애들의 DB 테이블에 적용해서 생성해라

// 엔티티의 변화를 감시하는 리스너
// @PrePersist,@PreUpdate 와 같은 큭정 시점을 감지하기 위해 사용
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @Column(name = "create_date", updatable = false, nullable = false) //
    private Date createdAt;

    @Column(name = "update_date", nullable = false ) // nullable = false 비어 있을 수 없음
    private Date updatedAt;

    @PrePersist // 저장이 될때 변경
    void createdAt(){
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate // 수정이 될때 변경
    void updateAt(){
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
