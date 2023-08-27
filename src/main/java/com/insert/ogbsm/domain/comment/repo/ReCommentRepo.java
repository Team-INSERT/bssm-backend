package com.insert.ogbsm.domain.comment.repo;

import com.insert.ogbsm.domain.comment.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReCommentRepo extends JpaRepository<ReComment, Long> {
}
