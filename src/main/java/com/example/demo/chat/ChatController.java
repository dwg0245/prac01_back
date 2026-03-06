package com.example.demo.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
// 소켓 컴피그에 주소에 이 주소에 오면 실행을 해주겠다.
public class ChatController {
    // WebSocketConfig 클래스에서 설정해둔 /app(클라이언트가 메세지를 보낼때 사용할 주소의 시작 부분) 뒤에 /test 로 메세지를 보내면 해당 메소드 실행
    // /app/test로 보내야 실행이 된다.
    @MessageMapping("/test")

    // 누구에게 메세지를 보낼 것인지
    // 구독자가 메세지를 받을 경로의 시작 부분 경로를 같이 적어줘야 한다.


    // WebSocketConfig 클래스에서 설정해둔 /topic 뒤에 /test 라는 토픽을 구독한 사용자들에게 return 값을 보낸다.
    // test라는 토픽을 구독한 클라이언트들에게 메세지 전송
    @SendTo("/topic/test")
    public String test(){
        System.out.println("test");

        // 다른사람에게는 리턴 메세지가 간다.
        return "zzzz";
    }

    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/chat/{roomIdx}")
    public void sendChatMessage(@DestinationVariable Long roomIdx,String message){
        // 특정 토픽 방에 있는 유저에게 메세지를 보내라
        messagingTemplate.convertAndSend("/topic/"+roomIdx,message);
    }
}
