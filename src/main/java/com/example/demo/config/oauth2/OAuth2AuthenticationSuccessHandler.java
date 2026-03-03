package com.example.demo.config.oauth2;

import com.example.demo.user.model.AuthUserDetails;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 소셜 성공 로그인 처리하는 클래스
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("OAuth 2.0 로그인 성공");

        // Dto 정보 불러오기
        AuthUserDetails user = (AuthUserDetails)authentication.getPrincipal();

        // 토큰 만들기
        String jwt = jwtUtil.createToken(user.getIdx(), user.getUsername(), user.getRole());
        response.addHeader("Set-Cookie", "ATOKEN=" + jwt + "; Path=/");

        String redirectUrl = "http://localhost:5173/success";

        // 프론트 주소로 리다이렉트 해주기
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
