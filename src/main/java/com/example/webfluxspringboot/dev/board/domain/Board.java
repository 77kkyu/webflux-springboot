package com.example.webfluxspringboot.dev.board.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("board")
public class Board {

    @Id
    private Long id;

    private Long userId;

    private String title;

    private String contents;

    @Builder
    public Board(Long userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }
}
