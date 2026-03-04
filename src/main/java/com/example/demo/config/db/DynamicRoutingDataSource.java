package com.example.demo.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

// 데이터 소스 매칭 클래스 상속
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    // 데이터 소스 재정의
    @Override
    protected Object determineCurrentLookupKey() {
        // 특정 조건을 기준으로 어떨때는 마스터가 어떨때는 슬래이브가
        // 서비스 코드에 조회 업데이트를 하면 하나라도 실행하면 둘다 실패, 성공인데, 이때 트렌젝션을 달아준다.ㅏ
        // @Transactional(readOnly = true)를 받아와서 맞으면 slave 실행
        Boolean isReadOnly = isCurrentTransactionReadOnly();
        String dataSourceName;
        if(isReadOnly) {
            dataSourceName = "SLAVE";
        } else {
            dataSourceName = "MASTER";
        }
        System.out.println("dataSourceName : " + dataSourceName);

        return dataSourceName;
    }
}
