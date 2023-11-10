package com.insert.ogbsm.service.comment.implement;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.comment.dto.CommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.service.post.implement.PostImplement;
import com.insert.ogbsm.service.user.implement.UserImplement;
import com.insert.ogbsm.service.validation.PostValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.insert.ogbsm.presentation.comment.dto.CommentRes.CommentDefRes;

@Service
@RequiredArgsConstructor
public class CommentImplement {
    private final CommentRepo commentRepo;
    private final PostImplement postImplement;
    private final LikesRepo likesRepo;
    private final PostValidation postValidation;
    private final UserImplement userImplement;

    public CommentDefRes append(Comment comment) {
        commentRepo.save(comment);

        return new CommentDefRes(comment.getPostId());
    }

    public Comment readById(Long id) {
        return commentRepo.findById(id)
                .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public void remove(Comment comment) {
        commentRepo.delete(comment);
    }

    public void increaseCommentCount(Long postId) {
        postValidation.checkPostExist(postId);

        Post post = postImplement.read(postId);
        post.increaseCommentCount();
    }

    public void decreaseCommentCount(Long postId) {
        Post post = postImplement.read(postId);
        post.decreaseCommentCount();
    }

    public Pagination<List<CommentRes>> indOrderByMineLikeRecent(Long postId, Long userId, Pageable pageable) {
        Page<Comment> postPage = commentRepo.findOrderByMineLikeRecent(postId, userId, pageable);
        List<CommentRes> comments = postPage.stream()
                .map(comment -> new CommentRes(
                                comment,
                                userImplement.readUser(comment.getUserId()),
                                checkDoesLikeComment(userId, comment)
                        )
                ).collect(Collectors.toList());

        return new Pagination<>(comments, postPage.getTotalPages(), pageable.getPageNumber());
    }

    public boolean checkDoesLikeComment(Long userId, Comment comment) {
        if (userId != null) {
            Optional<Likes> likes = likesRepo.findByUserIdAndPartyIdAndType(userId, comment.getId(), Type.COMMENT);
            return likes.isPresent();
        }

        return false;
    }
}
