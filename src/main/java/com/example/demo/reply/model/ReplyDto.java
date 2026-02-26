package com.example.demo.reply.model;


import lombok.Builder;
import lombok.Getter;


public class ReplyDto {
    @Builder
    @Getter
    public static class Replyres{
        private Long idx;
        private String contents;
        private String writer;

        public static ReplyDto.Replyres from(Reply entity){
            return ReplyDto.Replyres.builder()
                    .idx(entity.getIdx())
                    .contents(entity.getContents())
                    .writer(entity.getUser().getName())
                    .build();
        }
    }
}
