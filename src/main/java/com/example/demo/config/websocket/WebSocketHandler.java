package com.example.demo.config.websocket;

import com.example.demo.user.model.AuthUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    // 중복이 되지 않게 하기 위해서 set을 이용한다.
    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Override
    // 클라이언트의 연결 정보를 가지고 있음. session에

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("클라이언트가 웹 소켓 서버 접속함");
        sessions.add(session);

        session.getAttributes(); // 인터셉터에서 담아줬던 정보를 가지고 올 수 있음
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("메세지 전송 받음: {}", message.toString());

        // Attributes에 저장했던 유저정보를 가지고 온다.
        Authentication auth = (Authentication) session.getAttributes().get("user");
        AuthUserDetails user = (AuthUserDetails) auth.getPrincipal();

        System.out.println(user.getUsername());

        for (WebSocketSession to : sessions){
            // 접속한 사용자를 불러와서 같으면 안 보내면 되고
            if(to.equals(session)){
                continue;
            }
            // 접속한 사용자가 아니면 메세지를 보내면 된다.
            to.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("클라이언틑가 웹 소켓 서버 연결 해지함");
    }
}
