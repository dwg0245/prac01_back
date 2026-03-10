package com.example.demo.reply;

import com.example.demo.reply.model.Reply;
import com.example.demo.reply.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyDto.Replyres read(Long idx) {
        Reply reply = replyRepository.findById(idx).orElseThrow();
        return ReplyDto.Replyres.from(reply);
    }

    public ReplyDto.Replyres reg(Long boardIdx, Long userIdx, ReplyDto.RegReq dto) {
        Reply reply = dto.toEntity(boardIdx,userIdx);

        reply = replyRepository.save(reply);
        reply = replyRepository.findById(reply.getUser().getIdx()).orElseThrow();

        return ReplyDto.Replyres.from(reply);
    }
}
