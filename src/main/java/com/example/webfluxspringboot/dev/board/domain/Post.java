package com.example.webfluxspringboot.dev.board.domain;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("post")
public class Post {

	@Id
	private Long id;

	private Long userId;

	private String title;

	private String contents;

	public Post(){}

	@Builder
	public Post(Long id, Long userId, String title, String contents) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.contents = contents;
	}

}
