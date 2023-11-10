package com.insert.ogbsm.service.comment.implement;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.domain.comment.repo.ReCommentRepo;
import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.comment.dto.ReCommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.service.user.implement.UserImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReCommentImplement {
    private final ReCommentRepo reCommentRepo;
    private final CommentImplement commentImplement;
    private final UserImplement userImplement;
    private final LikesRepo likesRepo;


    public void increaseReCommentCount(Long commentId) {
        Comment comment = commentImplement.readById(commentId);
        comment.increaseReCommentCount();
    }

    public void append(ReComment reComment) {
        reCommentRepo.save(reComment);
    }

    public ReComment readById(Long id) {
        return reCommentRepo.findById(id)
                .orElseThrow(() -> new BsmException(ErrorCode.RECOMMENT_NOT_FOUND));
    }

    public void remove(ReComment reComment) {
        reCommentRepo.delete(reComment);
    }

    public void decreaseReCommentCount(ReComment reComment) {
        Comment comment = commentImplement.readById(reComment.getCommentId());
        comment.decreaseReCommentCount();
    }

    public Pagination<List<ReCommentRes>> readByCommentId(Long commentId, Long userId, Pageable pageable) {
        Page<ReComment> pageReComment = reCommentRepo.findByCommentIdOrderByLikeCountDescCreatedAtAsc(commentId, pageable);

        List<ReCommentRes> reComments = pageReComment.stream()
                .map(reComment -> new ReCommentRes(
                                reComment,
                                userImplement.readUser(reComment.getUserId()),
                                doesLikeComment(userId, reComment)
                        )
                )
                .collect(Collectors.toList());

        return new Pagination<>(reComments, pageReComment.getTotalPages(), pageable.getPageNumber());
    }

    private boolean doesLikeComment(Long userId, ReComment reComment) {
        if (userId != null) {
            Optional<Likes> likes = likesRepo.findByUserIdAndPartyIdAndType(userId, reComment.getId(), Type.RECOMMENT);
            return likes.isPresent();
        }

        return false;
    }
}
