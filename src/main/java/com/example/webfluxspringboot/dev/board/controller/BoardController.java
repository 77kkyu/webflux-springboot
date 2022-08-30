package com.example.webfluxspringboot.dev.board.controller;

import com.example.webfluxspringboot.dev.board.dto.request.BoardAddRequestDto;
import com.example.webfluxspringboot.dev.board.dto.response.BoardAddResponseDto;
import com.example.webfluxspringboot.dev.board.dto.response.BoardsResponseDto;
import com.example.webfluxspringboot.dev.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board/add")
    public Mono<BoardAddResponseDto> boardAdd(BoardAddRequestDto boardAddRequestDto) {
        return boardService.boardAdd(boardAddRequestDto);
    }

    @GetMapping("/board/{id}")
    public Mono<BoardsResponseDto> board(Long id) {
        return boardService.findBoard(id);
    }

    @GetMapping("/boards")
    public Mono<List<BoardsResponseDto>> boards() {
        return boardService.boards();
    }

}
