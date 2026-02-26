package com.example.demo.board.model;

import com.example.demo.reply.model.ReplyDto;
import com.example.demo.user.model.User;
import lombok.*;

import java.util.List;

public class BoardDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterReq{
        private String title;
        private String contents;

        public Board toEntity(Long userIdx){
            return Board.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .user(User.builder()
                            .idx(userIdx)
                            .build())
                    .build();
        }
    }

    @Getter
    public static class RegReq {
        private String title;
        private String contents;

        public Board toEntity() {
            return Board.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .build();
        }
    }



    @Builder
    @Getter
    public static class RegRes {
        private Long idx;
        private String title;
        private String contents;
        private Long userIdx;

        public static RegRes from(Board entity) {
            return RegRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .userIdx(entity.getUser().getIdx())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ListRes {
        private Long idx;
        private String title;
        private String username;
        private long replySize;
        private int likes;


        public static ListRes from(Board entity) {
            return ListRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .username(entity.getUser().getName())
                    .replySize(entity.getReplyList().size())
                    .likes(entity.getLikes().size())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ReadRes {
        private Long idx;
        private String title;
        private String contents;
        private String username;
        private List<ReplyDto.Replyres> reply;
        private int likes;

        public static ReadRes from(Board entity) {
            return ReadRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .username(entity.getUser().getName())
                    .reply(entity.getReplyList().stream().map(ReplyDto.Replyres::from).toList())
                    .likes(entity.getLikes().size())
                    .build();
        }
    }
}
