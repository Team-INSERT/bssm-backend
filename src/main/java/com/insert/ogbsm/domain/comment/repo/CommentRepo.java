package com.insert.ogbsm.domain.comment.repo;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.presentation.post.dto.PostResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    Page<PostResDto> findAllByPostId(Long postId, Pageable pageable);
}
