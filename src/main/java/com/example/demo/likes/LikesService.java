package com.example.demo.likes;

import com.example.demo.board.BoardRepository;
import com.example.demo.board.model.Board;
import com.example.demo.likes.model.Likes;
import com.example.demo.likes.model.LikesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;

    public LikesDto.RegRes reg(Long boardIdx, Long userIdx,  LikesDto.RegReq dto) {
        Likes likes = dto.toEntity(boardIdx,userIdx);
        likes = likesRepository.save(likes);

        // 보드의 숫자 하나 올려주기 1
        Board board = likes.getBoard();
        board.increaseLikeCount();
        boardRepository.save(board);

        return LikesDto.RegRes.from(likes);
    }
}
