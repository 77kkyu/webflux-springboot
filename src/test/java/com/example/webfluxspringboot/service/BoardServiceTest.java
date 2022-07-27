package com.example.webfluxspringboot.service;

import com.example.webfluxspringboot.dev.dto.BoardAddResponseDto;
import com.example.webfluxspringboot.dev.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
