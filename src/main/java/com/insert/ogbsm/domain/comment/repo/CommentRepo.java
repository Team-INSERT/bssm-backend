package com.insert.ogbsm.domain.comment.repo;

import com.insert.ogbsm.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
