package com.example.demo.likes;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.likes.model.LikesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/likes")
@RequiredArgsConstructor
@RestController
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/reg/{boardIdx}/{userIdx}")
    public ResponseEntity reg(@PathVariable Long boardIdx, @PathVariable Long userIdx, LikesDto.RegReq dto){
        LikesDto.RegRes resuslt = likesService.reg(boardIdx, userIdx, dto);

        return ResponseEntity.ok(BaseResponse.success(resuslt));
    }
}
