package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.domain.comment.repo.ReCommentRepo;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.comment.dto.ReCommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReCommentReadService {
    private final ReCommentRepo reCommentRepo;
    private final UserRepo userRepo;

    public Pagination<List<ReCommentRes>> read(Long commentId, Pageable pageable) {

        Page<ReComment> pageReComment = reCommentRepo.findByCommentIdOrderByLikeCountDescCreatedAtDesc(commentId, pageable);
        List<ReCommentRes> reComments = pageReComment.stream()
                .map(reComment -> new ReCommentRes(
                                reComment,
                                userRepo.findById(reComment.getUserId())
                                        .orElseThrow(() -> new EntityNotFoundException("ReComment User Not Found"))
                        )
                )
                .collect(Collectors.toList());

        return new Pagination<>(reComments, pageReComment.getTotalPages(), pageable.getPageNumber());
    }
}
