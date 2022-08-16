package com.example.webfluxspringboot.dev.board.repository;

import com.example.webfluxspringboot.dev.board.domain.Board;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BoardRepository extends ReactiveCrudRepository<Board, Long> {
}
