package com.example.demo.reply;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.reply.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/reply")
@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @RequestMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx){
        ReplyDto.Replyres result = replyService.read(idx);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

}
