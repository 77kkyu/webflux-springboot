package com.example.webfluxspringboot.repository;

import com.example.webfluxspringboot.dev.domain.Board;
import com.example.webfluxspringboot.dev.dto.BoardsResponseDto;
import com.example.webfluxspringboot.dev.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void select() {
        Flux<Board> boardList =  boardRepository.findAll();
        System.out.println("TEST : " + boardList.toString());
    }

    @Test
    public void boards() {
        Flux<Board> boardFlux = boardRepository.findAll();
        boardFlux.subscribe(System.out::println);
    }

}
