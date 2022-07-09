package com.example.webfluxspringboot.repository;

import com.example.webfluxspringboot.dev.domain.Board;
import com.example.webfluxspringboot.dev.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void save() {
        boardRepository.save(Board.builder().userId(1L).contents("테스트").title("제목").build());
    }

}
