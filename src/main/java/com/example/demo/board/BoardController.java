package com.example.demo.board;

import lombok.RequiredArgsConstructor;
import com.example.demo.board.model.BoardDto;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/list")
    public ResponseEntity list() {
        List<BoardDto.ListRes> dto = boardService.list();
        return ResponseEntity.ok(dto);
    }
}

