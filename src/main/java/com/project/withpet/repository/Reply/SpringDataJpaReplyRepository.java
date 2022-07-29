package com.project.withpet.repository.Reply;

import com.project.withpet.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaReplyRepository extends JpaRepository<Reply, Long>, ReplyRepository {
}
