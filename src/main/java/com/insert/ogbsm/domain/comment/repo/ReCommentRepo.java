package com.insert.ogbsm.domain.comment.repo;

import com.insert.ogbsm.domain.comment.ReComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReCommentRepo extends JpaRepository<ReComment, Long> {
    Page<ReComment> findByCommentIdOrderByLikeCountDescCreatedAtDesc(Long commentId, Pageable pageable);
}
