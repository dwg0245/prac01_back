package com.example.demo.reply;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.reply.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reply")
@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/reg/{boardIdx}/{userIdx}")
    public ResponseEntity reg(@PathVariable Long boardIdx,@PathVariable Long userIdx,@RequestBody ReplyDto.RegReq dto){
        ReplyDto.Replyres result = replyService.reg(boardIdx, userIdx, dto);

        return ResponseEntity.ok(BaseResponse.success(result));
    };


    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx){
        ReplyDto.Replyres result = replyService.read(idx);
        return ResponseEntity.ok(BaseResponse.success(result));
    }
}
