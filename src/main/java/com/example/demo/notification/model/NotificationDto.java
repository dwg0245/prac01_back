package com.example.demo.notification.model;

import lombok.Getter;

import java.util.Map;

public class NotificationDto {

    @Getter
    public static class Subscribe{
        private String endpoint;
        private Map<String, String> keys;

        public NotificationEntity toEntity(){
            return NotificationEntity.builder()
                    .endpoint(this.endpoint)
                    .p256dh(this.keys.get("p256dh"))
                    .auth(this.keys.get("auth"))
                    .build();
        }
    }


}
