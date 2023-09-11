package com.insert.ogbsm.service.like;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.domain.comment.repo.CommentRepo;
import com.insert.ogbsm.domain.comment.repo.ReCommentRepo;
import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.presentation.like.dto.LikesReq;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeDefService {
    private final LikesRepo likeRepo;
    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final ReCommentRepo reCommentRepo;

    public boolean changeLikeStatus(LikesReq likesReq, Long userId) {
        Optional<Likes> like = likeRepo.findByUserIdAndTypeAndPartyId(userId, likesReq.type(), likesReq.partyId());

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

    private void decreaseLikeCount(Likes likes) {
        if (likes.getType() == Type.POST) {
            Post post = postRepo.findById(likes.getPartyId())
                    .orElseThrow(() -> new EntityNotFoundException("post not found"));

            post.decreaseLike();
            return;
        } else if (likes.getType() == Type.COMMENT) {
            Comment comment = commentRepo.findById(likes.getPartyId())
                    .orElseThrow(() -> new EntityNotFoundException("comment not found"));

            comment.decreaseLike();
            return;
        }

        ReComment reComment = reCommentRepo.findById(likes.getPartyId())
                .orElseThrow(() -> new EntityNotFoundException("reComment not found"));

        reComment.decreaseLike();
    }

    private void addLikeCount(Likes likes) {
        if (likes.getType() == Type.POST) {
            Post post = postRepo.findById(likes.getPartyId())
                    .orElseThrow(() -> new EntityNotFoundException("post not found"));

            post.increaseLike();
            return;
        } else if (likes.getType() == Type.COMMENT) {
            Comment comment = commentRepo.findById(likes.getPartyId())
                    .orElseThrow(() -> new EntityNotFoundException("comment not found"));

            comment.increaseLike();
            return;
        }

        ReComment reComment = reCommentRepo.findById(likes.getPartyId())
                .orElseThrow(() -> new EntityNotFoundException("reComment not found"));

        reComment.increaseLike();
    }
}
