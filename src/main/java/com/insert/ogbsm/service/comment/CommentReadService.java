package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.comment.dto.CommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
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
    private final UserRepo userRepo;

    public Pagination<List<CommentRes>> readComments(Long postId, Pageable pageable) {
        Page<Comment> postPage = commentRepo.findAllByPostIdOrderByLikeCountDescCreatedAtDesc(postId, pageable);

        List<CommentRes> comments = postPage.stream()
                .map(comment -> new CommentRes(
                        comment,
                        userRepo.findById(comment.getUserId())
                                .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND))))
                .collect(Collectors.toList());

        return new Pagination<>(comments, postPage.getTotalPages(), pageable.getPageNumber());
    }
}
