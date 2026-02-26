package com.example.demo.likes.model;

import com.example.demo.board.model.Board;
import com.example.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class LikesDto {
    @Getter
    @Builder
    public static class RegReq{
        private Long idx;

        public Likes toEntity(Long boardIdx, Long userIdx){
            return Likes.builder()
                    .idx(this.idx)
                    .board(Board.builder()
                            .idx(boardIdx)
                            .build())
                    .user(User.builder()
                            .idx(userIdx)
                            .build())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class RegRes{
        private Long idx;
        private Long boardIdx;
        private Long userIdx;

        public static LikesDto.RegRes from(Likes entity){
            return RegRes.builder()
                    .idx(entity.getIdx())
                    .boardIdx(entity.getBoard().getIdx())
                    .userIdx(entity.getUser().getIdx())
                    .build();
        }
    }

}
