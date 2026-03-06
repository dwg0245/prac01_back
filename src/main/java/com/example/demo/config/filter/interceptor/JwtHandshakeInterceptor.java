package com.example.demo.config.filter.interceptor;

import com.example.demo.user.model.AuthUserDetails;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
// 핸드 쉐이크로 주입받아서 쓰겠다.
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 인터셉터에서 jwt 토큰을 가지고 있는 사용자 확인
        // jwt 토큰이 유효한 사용자만 이용할 수 있도록 설정

        // 토큰을 꺼내오는 작업을 한다.
        if (request instanceof ServletServerHttpRequest serverHttpRequest){
            HttpServletRequest httpReq = serverHttpRequest.getServletRequest();
            // 쿠키 가지고 오기
            if (httpReq.getCookies() != null) {
                for (Cookie cookie : httpReq.getCookies()) {
                    if (cookie.getName().equals("ATOKEN")) {
                        // JwtUtil에서 토큰 생성 및 확인하도록 리팩토링
                        Long idx = jwtUtil.getUserIdx(cookie.getValue());
                        String username = jwtUtil.getUsername(cookie.getValue());
                        String role = jwtUtil.getRole(cookie.getValue());

                        // 담아줘야 컨트롤러에서 받아 볼 수 있다.
                        AuthUserDetails user = AuthUserDetails.builder()
                                .idx(idx)
                                .username(username)
                                .role(role )
                                .build();

                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );

                        // session attributes 에 담아준다.
                        attributes.put("user",authentication);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {

    }
}
