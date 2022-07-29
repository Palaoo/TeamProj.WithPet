package com.project.withpet.service;

import com.project.withpet.domain.Reply;
import com.project.withpet.repository.Reply.ReplyRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void newReply(Reply reply) {
        replyRepository.save(reply);
    }

    public List<Reply> findList(Long boardcode){
        return replyRepository.findByBoardcode(boardcode);
    }
}
