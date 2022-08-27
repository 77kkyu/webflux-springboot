package com.example.webfluxspringboot.dev.board.repository;

import com.example.webfluxspringboot.dev.board.domain.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {
}
