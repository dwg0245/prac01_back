package com.example.demo.reply.model;


import com.example.demo.board.model.Board;
import com.example.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ReplyDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegReq{
        private String contents;

        public Reply toEntity(Long boardIdx, Long userIdx){
            return Reply.builder()
                    .contents(this.contents)
                    .board(Board.builder()
                            .idx(boardIdx)
                            .build())
                    .user(User.builder()
                            .idx(userIdx)
                            .build())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class Replyres{
        private Long idx;
        private String contents;
        private String writer;
        private Long boardIdx;
        private Long userIdx;

        public static ReplyDto.Replyres from(Reply entity){
            return ReplyDto.Replyres.builder()
                    .idx(entity.getIdx())
                    .contents(entity.getContents())
                    .writer(entity.getUser().getName())
                    .boardIdx(entity.getBoard().getIdx())
                    .userIdx(entity.getUser().getIdx())
                    .build();
        }
    }
}
