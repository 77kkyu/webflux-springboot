package com.example.webfluxspringboot.dev.repository;


import com.example.webfluxspringboot.dev.domain.Board;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BoardRepository extends ReactiveCrudRepository<Board, Long> {
}
