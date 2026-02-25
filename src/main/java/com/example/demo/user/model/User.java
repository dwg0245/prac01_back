package com.example.demo.user.model;

import com.example.demo.board.model.Board;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Lazy;

import javax.swing.border.Border;
import java.util.List;

// 일
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String email;
    private String name;
    @Setter
    private String password;
    @Setter
    private boolean enable;
    private String role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> borderList;
}
