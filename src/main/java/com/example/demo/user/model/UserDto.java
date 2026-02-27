package com.example.demo.user.model;

import com.example.demo.board.model.Board;
import com.example.demo.board.model.BoardDto;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class UserDto {

    @Getter
    public static class SignupReq {
        // 이 패턴 아니면 안됨(입력값 검사), 예외 메세지 작성, 정규표현식 적어주기
        // 콘솔에 왜 틀렸는지 알려줌
        @Pattern(message = "이메일 형식이 아닙니다.", regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$/i")
        private String email; // test01@test.com O, test01,test@err X

        // 한글만 가능하게
        @Pattern(message = "한글만 입력해주세요" , regexp = "^[가-힣]*$")
        private String name;

        @Pattern(message = "비밀번호는 숫자, 영문, 특수문자( !@#$%^&*() )를 조합해 8~20자로 생성해주세요.", regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()])(?=.*[0-9]).{8,20}$")
        private String password;

        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
                    .enable(false)
                    // .role("ROLE_USER") 직접 쓰기 보다는 // User 에티티에 도메인 무결성으로 해주기
                    .build();
        }
    }


    @Builder
    @Getter
    public static class SignupRes {
        private Long idx;
        private String email;
        private String name;

        public static SignupRes from(User entity) {
            return SignupRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .build();
        }
    }

    @Getter
    public static class LoginReq {
        private String email;
        private String password;
    }

    @Builder
    @Getter
    public static class LoginRes {
        private Long idx;
        private String email;
        private String name;

        public static LoginRes from(User entity) {
            return LoginRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .build();
        }
    }
}
