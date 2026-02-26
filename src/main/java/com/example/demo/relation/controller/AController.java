package com.example.demo.relation.controller;

import com.example.demo.relation.service.AService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/a")
@RequiredArgsConstructor
@RestController
public class AController {
    private final AService aService;

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx){
        aService.read(idx);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/read/list")
    public ResponseEntity readList(){
        aService.readList();

        return ResponseEntity.ok("성공");
    }
}
