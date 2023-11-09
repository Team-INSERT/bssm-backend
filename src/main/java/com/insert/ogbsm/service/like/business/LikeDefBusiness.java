package com.insert.ogbsm.service.like.business;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.comment.repo.ReCommentRepo;
import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostWrapper;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.like.dto.LikesReq;
import com.insert.ogbsm.service.validation.PostValidation;
import com.insert.ogbsm.service.validation.UserValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeDefBusiness {
    private final LikesRepo likeRepo;
    private final PostWrapper postWrapper;
    private final PostValidation postValidation;
    private final UserValidation userValidation;
    private final CommentRepo commentRepo;
    private final ReCommentRepo reCommentRepo;

    public boolean changeLikeStatus(LikesReq likesReq, Long userId) {
        Optional<Likes> like = likeRepo.findByUserIdAndTypeAndPartyId(userId, likesReq.type(), likesReq.partyId());

        validation(userId, likesReq.partyId(), likesReq.type());

        if (like.isEmpty()) {
            Likes likes = likesReq.toEntity(userId);

            likeRepo.save(likes);
            addLikeCount(likes);
            return true;
        }

        Likes likes = like.get();
        likeRepo.delete(likes);
        decreaseLikeCount(likes);
        return false;
    }

    private void validation(Long userId, Long partyId, Type type) {
        userValidation.checkUserExist(userId);

        if (type == Type.POST) {
            postValidation.checkPostExist(partyId);
            return;
        } else if (type == Type.COMMENT) {
            commentRepo.findById(partyId)
                    .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND));
            return;
        }
        reCommentRepo.findById(partyId)
                .orElseThrow(() -> new BsmException(ErrorCode.RECOMMENT_NOT_FOUND));
    }

    private void decreaseLikeCount(Likes likes) {
        if (likes.getType() == Type.POST) {
            Post post = postWrapper.getPost(likes.getPartyId());

            post.decreaseLike();
            return;
        } else if (likes.getType() == Type.COMMENT) {
            Comment comment = commentRepo.findById(likes.getPartyId())
                    .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND));

            comment.decreaseLike();
            return;
        }

        ReComment reComment = reCommentRepo.findById(likes.getPartyId())
                .orElseThrow(() -> new BsmException(ErrorCode.RECOMMENT_NOT_FOUND));

        reComment.decreaseLike();
    }

    private void addLikeCount(Likes likes) {
        if (likes.getType() == Type.POST) {
            Post post = postWrapper.getPost(likes.getPartyId());
            post.increaseLike();
            return;

        } else if (likes.getType() == Type.COMMENT) {
            Comment comment = commentRepo.findById(likes.getPartyId())
                    .orElseThrow(() -> new BsmException(ErrorCode.COMMENT_NOT_FOUND));
            comment.increaseLike();
            return;
        }

        ReComment reComment = reCommentRepo.findById(likes.getPartyId())
                .orElseThrow(() -> new BsmException(ErrorCode.RECOMMENT_NOT_FOUND));

        reComment.increaseLike();
    }
}
