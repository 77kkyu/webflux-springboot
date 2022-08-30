package com.example.webfluxspringboot.dev.board.service;

import com.example.webfluxspringboot.dev.board.domain.Board;
import com.example.webfluxspringboot.dev.board.dto.request.BoardAddRequestDto;
import com.example.webfluxspringboot.dev.board.dto.response.BoardAddResponseDto;
import com.example.webfluxspringboot.dev.board.dto.response.BoardsResponseDto;
import com.example.webfluxspringboot.dev.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public void boardAdd(BoardAddResponseDto boardAddResponseDto) {
        boardRepository.save(Board.builder()
            .title(boardAddResponseDto.getTitle())
            .contents(boardAddResponseDto.getContents())
            .userId(boardAddResponseDto.getUserId())
            .build()
        ).subscribe();
    }

    public Flux<Board> getBoard() {
        return boardRepository.findAll();
    }

    public Mono<BoardAddResponseDto> boardAdd(BoardAddRequestDto boardAddRequestDto) {
        return boardRepository.save(Board.builder()
            .title(boardAddRequestDto.getTitle())
            .contents(boardAddRequestDto.getContents())
            .userId(boardAddRequestDto.getUserId())
            .build()
        ).map(board -> {
            return BoardAddResponseDto.builder()
                .title(board.getTitle())
                .contents(board.getContents())
                .userId(board.getUserId())
                .build();
        }).doOnError(RuntimeException -> {
            throw new RuntimeException("board add error");
        });
    }

    public Mono<List<BoardsResponseDto>> boards() {
        return boardRepository.findAll()
            .map(board -> {
                return BoardsResponseDto.builder()
                    .id(board.getId())
                    .contents(board.getContents())
                    .title(board.getTitle())
                    .userId(board.getUserId())
                    .build();
            }).collectList();
    }

    public Flux boardNewTitle() {
        Flux flux = (Flux) boardRepository.findAll()
                .filter(e -> e.getTitle() == null)
                .map(x -> x.getTitle() == "새 제목")
                .subscribe();
        return flux;
    }

    public Mono findBoard(Long id) {
        return boardRepository.findById(id);
    }

}
