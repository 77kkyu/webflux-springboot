package com.example.webfluxspringboot.dev.board.dto.response;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class BoardsResponseDto {

    private Long id;

    private Long userId;

    private String title;

    private String contents;

    @Builder
    public BoardsResponseDto(Long id, Long userId, String title, String contents) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }

}
