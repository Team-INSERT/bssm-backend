package com.insert.ogbsm.domain.comment.repo;

import com.insert.ogbsm.domain.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.postId = :postId ORDER BY CASE WHEN c.userId = :userId THEN 0 ELSE 1 END, c.likeCount DESC, c.createdAt ASC")
    Page<Comment> findByPostIdOrderByLikeDesc(Long postId, Long userId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.postId = :postId ORDER BY CASE WHEN c.userId = :userId THEN 0 ELSE 1 END, c.createdAt ASC")
    Page<Comment> findByPostOrderByCreatedAtDesc(Long postId, Long userId, Pageable pageable);
}
