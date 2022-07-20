package com.project.withpet.repository;

import com.project.withpet.domain.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    Reply save(Reply reply);
    List<Reply> findByBoardcode(Long boardcode);
}
