package com.example.webfluxspringboot.dev.board.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardAddRequestDto {

	private Long userId;

	private String title;

	private String contents;

}
