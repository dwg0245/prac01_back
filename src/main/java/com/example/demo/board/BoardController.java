package com.example.demo.board;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.user.model.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import com.example.demo.board.model.BoardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RequestMapping("/board")
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/reg")
    public ResponseEntity register(@RequestBody BoardDto.RegReq dto) {
        BoardDto.RegRes result = boardService.register(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reg/{userIdx}")
    public ResponseEntity reg(@PathVariable Long userIdx, @RequestBody BoardDto.RegisterReq dto){
        BoardDto.RegRes result = boardService.reg(userIdx,dto);

        return ResponseEntity.ok(BaseResponse.success(result));
    }



    // 패이지 번호는 0번 부터/ 몇개씩 잘라서 보겠다.
    @GetMapping("/list")
    public ResponseEntity list(
            // 기본값을 설정해주기, 반드시 존재, 기본 설정값
            @RequestParam(required = true, defaultValue = "0") int page,
            @RequestParam(required = true, defaultValue = "5")int size) {
        //List<BoardDto.ListRes> dto = boardService.list(page,size);
        BoardDto.PageRes dto = boardService.list(page,size);
        return ResponseEntity.ok(BaseResponse.success(dto));
    }

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx) {
        BoardDto.ReadRes dto = boardService.read(idx);
        return ResponseEntity.ok(BaseResponse.success(dto));
    }

    @PutMapping("/update/{idx}")
    public ResponseEntity update(@PathVariable Long idx, @RequestBody BoardDto.RegReq dto) {
        BoardDto.RegRes returnDto = boardService.update(idx, dto);
        return ResponseEntity.ok(BaseResponse.success(returnDto));
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity update(@PathVariable Long idx) {
        boardService.delete(idx);
        return ResponseEntity.ok(BaseResponse.success("성공"));
    }
}

