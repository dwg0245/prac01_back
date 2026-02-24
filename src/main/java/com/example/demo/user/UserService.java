package com.example.demo.user;

import com.example.demo.user.model.User;
import com.example.demo.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserDto.SignupRes signup(UserDto.SignupReq dto) {
        User user = dto.toEntity();
        userRepository.save(user);

        return UserDto.SignupRes.from(user);
    }
}
