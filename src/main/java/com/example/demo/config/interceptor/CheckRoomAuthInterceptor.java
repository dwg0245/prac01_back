package com.example.demo.config.interceptor;

import com.example.demo.user.model.AuthUserDetails;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
// 방에 권한이 있는지 없는지 확인하는 클래스
public class CheckRoomAuthInterceptor implements ChannelInterceptor {
    @Override
    public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(message);

        if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
            String path = accessor.getDestination();

            // 사용자 번호 받아오기
            AuthUserDetails user = (AuthUserDetails) accessor.getUser();

            // 토픽으로 시작을 하면
            if(path.startsWith("/topic/")){
                //      방 번호를 가지고 오기
                //      사용자 번호도 가져올 수 있음
                //      현재 반에 사용자가 있으면 권한이 있는지 없는지 알 수 있다?
                String roomIdx = path.substring("/topic/".length());

                // db에서 사용자 번호를 통해서 권한이있는 방번호를 조회 해야 한다.
                List<Long> roomIdxList = List.of(3L,4L); // 이거는 임시
                // 속해 있지 않으면 예외를 던지다.
                if(!roomIdxList.contains(Long.parseLong(roomIdx))){
                    throw new IllegalArgumentException();
                }
            }

        }
        // 속해 있으면 메세지를 보낸다.
        return message;
    }
}
