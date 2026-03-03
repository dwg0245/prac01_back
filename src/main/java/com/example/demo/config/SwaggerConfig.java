package com.example.demo.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;


// OpenAPI 규격의 API 명세서를 자동으로 생성하기 위한 설정
// http://localhost:8080/swagger-ui/index.html
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BE24 스프링 API") // 문서 이름 설정
                        .description("")
                        .version("1.0.0"));     // API의 버전 (현재 "1.0.0")
    }
}
