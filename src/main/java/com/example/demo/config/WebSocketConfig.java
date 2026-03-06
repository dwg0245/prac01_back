package com.example.demo.config;

import com.example.demo.config.filter.interceptor.JwtHandshakeInterceptor;
import com.example.demo.config.websocket.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

// 웹소켓 설정
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    // WebSocketHandlerd의 afterConnectionEstablished를 실행한다.
    private final WebSocketHandler webSocketHandler;
    // 인터셉터 주입
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 웹 소켓은 따로 보안 설정이 있다./ .setAllowedOrigins("*")
        // 크리덴셜 설정을 해줘야 한다. (쿠기를 받으려면)
        registry.addHandler(webSocketHandler,"/ws")

                .setAllowedOrigins("*")
                .addInterceptors(jwtHandshakeInterceptor);
    }
}
