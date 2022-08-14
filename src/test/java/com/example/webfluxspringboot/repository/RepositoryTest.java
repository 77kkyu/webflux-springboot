package com.example.webfluxspringboot.repository;

import com.example.webfluxspringboot.dev.board.domain.Board;
import com.example.webfluxspringboot.dev.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
public class RepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void boards() {
        Flux<Board> boardFlux = boardRepository.findAll();
        boardFlux.subscribe(e -> System.out.println("test : " + e.toString()));
    }

    @Test
    public void addBoard() {
        Mono<Board> add = boardRepository.save(Board.builder()
                        .title("제목")
                        .contents("내용")
                .build());
        add.subscribe();
    }

    @Test
    public void findBoard() {
        boardRepository.findById(1L).subscribe(e -> System.out.println("test : " + e.toString()));
    }

}
