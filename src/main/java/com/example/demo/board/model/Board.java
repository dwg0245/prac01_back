package com.example.demo.board.model;

import com.example.demo.common.model.BaseEntity;

import com.example.demo.likes.model.Likes;
import com.example.demo.reply.model.Reply;
import com.example.demo.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// 다
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 무결성 제약 조건 달기
    // nullable = false - 비워두면 안된다.| length = 100 - 글자 수 제한
    @Column(nullable = false, length = 100)
    private String title;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Reply> replyList;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Likes> likes;


    public void update(BoardDto.RegReq dto) {
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }
}
