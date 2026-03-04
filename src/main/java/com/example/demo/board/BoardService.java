package com.example.demo.board;

import com.example.demo.board.model.Board;
import lombok.RequiredArgsConstructor;
import com.example.demo.board.model.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardDto.RegRes register(BoardDto.RegReq dto) {
        Board entity = boardRepository.save(dto.toEntity());

        return BoardDto.RegRes.from(entity);
    }

    // Transactional(readOnly = true) 가 안 달려 있으면 master에서 실행
    public BoardDto.PageRes list(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);

        // 페이징 처리 O, 페이지 번호가 필요하다 => Page로 반환
        // 페이징 처리 O, 페이지 번호가 필요없다 => Slice 반환, 다음 사이즈의 크기만큼 가져온다.
        // Slice<Board> result = boardRepository.findAll(pageRequest); // 무한스크롤
        Page<Board> result = boardRepository.findAll(pageRequest);

        return BoardDto.PageRes.from(result);
        //return result.stream().map(BoardDto.ListRes::from).toList();
    }

    // 데이터 소스 객체 읽어 오기
    @Transactional(readOnly = true)
    public BoardDto.ReadRes read(Long idx) {
        Board board = boardRepository.findById(idx).orElseThrow();
        return BoardDto.ReadRes.from(board);
    }

    // SQL문이 두개 이상 일어나면 이렇게 사용해서 하나로 묶어주기
    @Transactional
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
