package com.example.demo.board;

import com.example.demo.board.model.Board;
import lombok.RequiredArgsConstructor;
import com.example.demo.board.model.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardDto.RegRes register(BoardDto.RegReq dto) {
        Board entity = boardRepository.save(dto.toEntity());

        return BoardDto.RegRes.from(entity);
    }

    public BoardDto.PageRes list(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);

        // 페이징 처리 O, 페이지 번호가 필요하다 => Page로 반환
        // 페이징 처리 O, 페이지 번호가 필요없다 => Slice 반환, 다음 사이즈의 크기만큼 가져온다.
        // Slice<Board> result = boardRepository.findAll(pageRequest); // 무한스크롤
        Page<Board> result = boardRepository.findAll(pageRequest);

        return BoardDto.PageRes.from(result);
        //return result.stream().map(BoardDto.ListRes::from).toList();
    }

    public BoardDto.ReadRes read(Long idx) {
        Board board = boardRepository.findById(idx).orElseThrow();
        return BoardDto.ReadRes.from(board);
    }

    public BoardDto.RegRes update(Long idx, BoardDto.RegReq dto) {
        Board board = boardRepository.findById(idx).orElseThrow();
        board.update(dto);

        boardRepository.save(board);

        return BoardDto.RegRes.from(board);
    }

    public void delete(Long idx) {
        boardRepository.deleteById(idx);
    }

    public BoardDto.RegRes reg(Long userIdx, BoardDto.RegisterReq dto) {
        Board board = dto.toEntity(userIdx);
        board = boardRepository.save(board);

        return BoardDto.RegRes.from(board);
    }
}
