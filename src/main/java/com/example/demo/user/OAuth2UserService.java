package com.example.demo.user;

import com.example.demo.user.model.AuthUserDetails;
import com.example.demo.user.model.User;
import com.example.demo.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("서비스 코드 실행");

        // 어떤걸로 로그인을 했는지 받아 올 수 있다.
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 카카오와 통신 후 응답
        // OAuth2 로그인 설정, properties로 들어가 있음
        // 인가 코드를 받아오면 실행이 되기에 실행이 되면 무조건 성공 로직이다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 내 서비스의 DTO로 변환
        UserDto.OAuth dto = UserDto.OAuth.from(oAuth2User.getAttributes(), "kakao");

        // DB에 회원이 있나 없나 확인
        Optional<User> result = userRepository.findByEmail(dto.getEmail());

        // 없으면 가입 시켜주기
        if(!result.isPresent()){
           User user = userRepository.save(dto.toEntity());
            return AuthUserDetails.from(user);
        }
        else { // 있으면 해당 사용자 반환
            User user = result.get();

            return AuthUserDetails.from(user);
        }
    }
}
