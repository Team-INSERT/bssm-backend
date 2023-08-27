package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.presentation.post.dto.PostResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReadService {
    private final CommentRepo commentRepo;

    public List<PostResDto> readComments(Long postId) {
        return commentRepo.findByPostId(postId)
    }
}
