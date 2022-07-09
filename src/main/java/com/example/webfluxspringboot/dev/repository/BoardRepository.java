package com.example.webfluxspringboot.dev.repository;

import com.example.webfluxspringboot.dev.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
