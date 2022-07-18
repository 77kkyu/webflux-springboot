package com.example.webfluxspringboot.dev.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("board")
public class Board {

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
