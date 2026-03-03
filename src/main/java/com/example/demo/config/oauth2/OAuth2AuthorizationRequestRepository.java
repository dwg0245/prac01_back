package com.example.demo.config.oauth2;

import com.example.demo.utils.Aes256;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

// 이런 작업을 해주는 이유가 세션을 아예 사용하지 않기 위해서
@Component
public class OAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository {
    // 사용자 쿠키 설정
    @Override
    // 실행이 되지 않는 메소드, 리다이렉트를 뭐 어떻게 해야됨
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        for(Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("OAUTH2_REQUEST")){
                OAuth2AuthorizationRequest oAuth2AuthorizationRequest =
                (OAuth2AuthorizationRequest)SerializationUtils.deserialize(cookie.getValue().getBytes());

                return oAuth2AuthorizationRequest;
            }
        }
        return null;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        // 사용자 웹 브라우저에 쿠키 저장

        // 첫번째 요청(카카오 로그인을 누를 때)이 들어올 때 쿠키를 만들어서 보내준다.
        // 쿠키 객체 만들어서 쿠키 세팅하기
        Cookie cookie = new Cookie("OAUTH2_REQUEST",
                // 복호화 가능한 알고리즘으로 쿠키 세팅
                Aes256.encrypt(SerializationUtils.serialize(authorizationRequest)));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge((int) Duration.ofSeconds(300L).toSeconds());

        response.addCookie(cookie);

        // 이렇게 작성해도 되고 클래스 이용해도 됨
        //  response.addHeader("Set-Cookie", "ATOKEN=" + jwt + "; Path=/");
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        // 세션 삭제
        // 쿠키를 받아와서 쿠키를 삭제해주기
        for(Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("OAUTH2_REQUEST")){
                OAuth2AuthorizationRequest oAuth2AuthorizationRequest =
                        (OAuth2AuthorizationRequest)SerializationUtils.deserialize(
                                Aes256.decrypt(cookie.getValue().getBytes(StandardCharsets.UTF_8)));

                // 응답을 주기 전에 쿠키 다시 세팅
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setMaxAge((int) Duration.ofSeconds(0L).toSeconds()); // 알아서 만료되면 삭제 됨
                response.addCookie(cookie);

                return oAuth2AuthorizationRequest;
            }
        }
        return null;
    }
}
