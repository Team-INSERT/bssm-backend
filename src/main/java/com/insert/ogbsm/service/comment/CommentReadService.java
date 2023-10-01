package com.insert.ogbsm.service.comment;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.common.OrderType;
import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.domain.user.repo.UserWrapper;
import com.insert.ogbsm.presentation.comment.dto.CommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService {
    private final CommentRepo commentRepo;
    private final UserWrapper userWrapper;
    private final LikesRepo likesRepo;

    public Pagination<List<CommentRes>> readComments(Long postId, Long userId, Pageable pageable, OrderType orderType) {
        Page<Comment> postPage = commentRepo.findByPostIdOrderByLikeDesc(postId, userId, pageable);

        if (orderType == OrderType.RECENT) {
            postPage = commentRepo.findByPostOrderByCreatedAtDesc(postId, userId, pageable);
        }

        List<CommentRes> comments = postPage.stream()
                .map(comment -> new CommentRes(
                                comment,
                                userWrapper.getUser(comment.getUserId()),
                                doesLikeComment(userId, comment)
                        )
                ).collect(Collectors.toList());

        return new Pagination<>(comments, postPage.getTotalPages(), pageable.getPageNumber());
    }

    private boolean doesLikeComment(Long userId, Comment comment) {
        if (userId != null) {
            Optional<Likes> likes = likesRepo.findByUserIdAndPartyIdAndType(userId, comment.getId(), Type.COMMENT);
            return likes.isPresent();
        }

        return false;
    }
}
