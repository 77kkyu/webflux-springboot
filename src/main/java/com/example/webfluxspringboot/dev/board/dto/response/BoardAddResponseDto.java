package com.example.webfluxspringboot.dev.board.dto.response;

import lombok.*;
import lombok.Builder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BoardAddResponseDto {

    private Long id;

    private Long userId;

    private String title;

    private String contents;

    @Builder
    public BoardAddResponseDto(Long userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }
}
