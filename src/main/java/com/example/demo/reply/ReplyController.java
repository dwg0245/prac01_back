package com.example.demo.reply;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.reply.model.ReplyDto;
import com.example.demo.user.model.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reply")
@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx){
        ReplyDto.Replyres result = replyService.read(idx);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @PostMapping("/reg/{boardIdx}")
    public ResponseEntity reg(@AuthenticationPrincipal AuthUserDetails user, @PathVariable Long boardIdx){
        return  ResponseEntity.ok(BaseResponse.success("성공"));
    }

}
