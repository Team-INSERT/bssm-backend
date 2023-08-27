package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.presentation.comment.dto.CommentResDto;
import com.insert.ogbsm.presentation.post.dto.PostResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService {
    private final CommentRepo commentRepo;

    public List<CommentResDto> readComments(Long postId, Pageable pageable) {
        return commentRepo.findAllByPostId(postId, pageable)
                .stream()
                .map(CommentResDto::new)
                .collect(Collectors.toList());
    }
}
