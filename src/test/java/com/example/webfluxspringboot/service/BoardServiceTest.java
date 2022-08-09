package com.example.webfluxspringboot.service;

import com.example.webfluxspringboot.dev.domain.Board;
import com.example.webfluxspringboot.dev.dto.BoardAddResponseDto;
import com.example.webfluxspringboot.dev.dto.BoardsResponseDto;
import com.example.webfluxspringboot.dev.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void boardAdd() {
        BoardAddResponseDto boardAddResponseDto = new BoardAddResponseDto();
        boardService.boardAdd(boardAddResponseDto);
    }

    @Test
    public void getBoard() {
        boardService.getBoard().subscribe();
    }

    @Test
    public void getBoards() {
        boardService.boards().subscribe(System.out::println);
    }

}
