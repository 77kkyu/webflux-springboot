package com.example.webfluxspringboot.service;

import com.example.webfluxspringboot.dev.board.dto.response.BoardAddResponseDto;
import com.example.webfluxspringboot.dev.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
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

//    @Test
//    public void getBoards() {
//        boardService.boards().subscribe(System.out::println);
//    }

}
