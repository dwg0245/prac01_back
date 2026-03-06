package com.example.demo.notification;

import com.example.demo.notification.model.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    // 푸시 서비스 의존성 주입

    // 의존성 주입 생성자 직접 만들기

    // 만약에
    //      라이브러리로 동작할 수 잇도록 설정



    // 푸시 서비스는 객체를 생성해 준다.
    // 이때 공개키(긴거)랑 개인키(짧은거)를 같이 등록해 준다. , 이렇게 하면 이게 등록된 것맡 알림을 보낼 수 있음
    // 알림 보낼때 기본 제목 설정

    public void subscribe(NotificationDto.Subscribe dto) {
        notificationRepository.save(dto.toEntity());
    }

    public void send(NotificationDto.Send dto) {
        // 개인이 가지고 인증을 할 수 있게
        // DB에 자징된 무언가의 정보를 가지고 와서

        // --- 알림을 보낸다.
        // 키를 만들어주기 ( 보안이 중요하다)
        // endpoint 가벼오고
        // p256 가져오고
        // auth 가져오고

        // payload이 Json의 형태가 되어야 한다.

        // 웹 푸시로 등록된 정보를 가지고 오고
        // 라이브러리 클래스를 가지고 와서 알림 객체 만들어서
        // 알림을 보낸다.


    }


}
