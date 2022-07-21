package com.example.webfluxspringboot.dev.service;

import com.example.webfluxspringboot.dev.domain.Board;
import com.example.webfluxspringboot.dev.dto.BoardAddResponseDto;
import com.example.webfluxspringboot.dev.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public void boardAdd(BoardAddResponseDto boardAddResponseDto) {
        boardRepository.save(Board.builder()
                .title(boardAddResponseDto.getTitle())
                .contents(boardAddResponseDto.getContents())
                .userId(boardAddResponseDto.getUserId())
                .build());
    }

    public Flux<Board> getBoard() {
        return boardRepository.findAll();
    }
}
