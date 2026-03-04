package com.example.demo.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration  // 설정파일이니까 이 어노테이션
public class DataSourceConfig {
    // 마스터와 슬래이브를 각각의 객체로 스프링 빈으로 등록했다.
    // 스프링에 객체로 등록
    @ConfigurationProperties(prefix = "spring.datasource.master") // yml 설정 가져오기
    @Bean  // 남이 만든거 쓸 때 이렇게
    // 데이터 소스 객체
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @ConfigurationProperties(prefix = "spring.datasource.slave")
    @Bean
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 명시한 객체가 잘 Bean으로 등록되면
    // DependsOn - master와 slave가 만들어져야 실행이 되도록 설정
    @DependsOn({"masterDataSource", "slaveDataSource"})
    @Bean
    public DataSource routingDataSource(
            // 의존성 주입
            // 각 구분하기 위해
            // Qualifier 이거를 사용해서 첫번째 만드는거는 첫번째에, 두번째 만드는거에 두번째에 전달
            @Qualifier("masterDataSource") DataSource master,
            @Qualifier("slaveDataSource") DataSource slave
    ){
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        // 마스터에 마스터 넣기 (맵형태의 데이터에)
        dataSourceMap.put("MASTER", master);
        // 슬레이브에 슬레이브 넣기 (맵형태의 데이터에)
        dataSourceMap.put("SLAVE", slave);

        // 등록해주기
        routingDataSource.setTargetDataSources(dataSourceMap);
        // 일단 기본은 master에 실행을 해라
        routingDataSource.setDefaultTargetDataSource(master);

        return routingDataSource;
    }

    @DependsOn({"routingDataSource"})
    // 똑같은 타입의 스프링 Bean이 있을 때 최우선으로 해당 Bean을 사용하게 하는 어노테이션
    @Primary
    @Bean
    public DataSource dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

}