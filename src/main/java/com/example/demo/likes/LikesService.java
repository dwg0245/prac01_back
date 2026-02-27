package com.example.demo.likes;

import com.example.demo.likes.model.Likes;
import com.example.demo.likes.model.LikesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;

    public LikesDto.RegRes reg(Long boardIdx, Long userIdx,  LikesDto.RegReq dto) {
        Likes likes = dto.toEntity(boardIdx,userIdx);
        likes = likesRepository.save(likes);

        return LikesDto.RegRes.from(likes);
    }
}
