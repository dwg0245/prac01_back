package com.example.demo.relation.controller;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.relation.model.BDto;
import com.example.demo.relation.service.BService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/b")
@RequiredArgsConstructor
@RestController
public class BController {
    private final BService bService;

    // 작성 기능
    @PostMapping("/reg/{aIdx}")
    public ResponseEntity reg(@PathVariable Long aIdx, @RequestBody BDto.BReq dto){
        BDto.BRes result = bService.reg(aIdx, dto);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx){
        bService.read(idx);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/read/list")
    public ResponseEntity readList(){
        bService.readList();

        return ResponseEntity.ok("성공");
    }


}
