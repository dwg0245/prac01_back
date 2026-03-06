package com.example.demo.config;

import com.example.demo.config.interceptor.JwtHandshakeInterceptor;
import com.example.demo.config.interceptor.CheckRoomAuthInterceptor;
import com.example.demo.config.websocket.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

// 웹소켓 설정
@Configuration

// 메세지 브로커 어노테이션
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
// 다시 접속 했을 때 메세지 가져오기
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // WebSocketHandlerd의 afterConnectionEstablished를 실행한다.
    private final WebSocketHandler webSocketHandler;
    // 인터셉터 주입
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final CheckRoomAuthInterceptor checkRoomAuthInterceptor;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("*");
        // 웹 브라우저에서 WS 프로토콜을 지원하지 않는 경우 WS 대신에 HTTP로 통신할 수 있게 해주는 라이브러리를 사용할 때 설정
        //.withSockJS();

    }

    // 구독을 할때 인터셉터가 돌아야한다.
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
    }

    @Override
    // 구독한 사용자 모두 메세지를 보낸다.
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독자가 메세지를 받을 경로의 시작 부분
        registry.enableSimpleBroker("/topic");
        // 클라이언트가 메세지를 보낼때 사용할 주소의 시작 부분
        registry.setApplicationDestinationPrefixes("/app");
        // 특정 사용자에게 메세지를 보낼 떄 사용할 주소의 시작 부분
        registry.setUserDestinationPrefix("/user");

    }

    //    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // 웹 소켓은 따로 보안 설정이 있다./ .setAllowedOrigins("*")
//        // 크리덴셜 설정을 해줘야 한다. (쿠기를 받으려면)
//        registry.addHandler(webSocketHandler,"/ws")
//
//                .setAllowedOrigins("*")
//                .addInterceptors(jwtHandshakeInterceptor);
//    }
}
