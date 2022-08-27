package com.example.webfluxspringboot.dev.board.service;

import com.example.webfluxspringboot.dev.board.repository.PostRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostService {

	private final PostRepository postRepository;

}
