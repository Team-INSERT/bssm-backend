package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.presentation.post.dto.PostResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService {
    private final CommentRepo commentRepo;

    public Page<PostResDto> readComments(Long postId, Pageable pageable) {
        return commentRepo.findAllByPostId(postId, pageable);
    }
}
