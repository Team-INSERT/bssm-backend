package com.insert.ogbsm.service.like.implement;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.comment.ReComment;
import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.service.comment.implement.CommentImplement;
import com.insert.ogbsm.service.comment.implement.ReCommentImplement;
import com.insert.ogbsm.service.post.implement.PostImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesImplement {
    private final LikesRepo likesRepo;
    private final PostImplement postImplement;
    private final CommentImplement commentImplement;
    private final ReCommentImplement reCommentImplement;

    public Optional<Likes> readByUserIdAndPartyIdAndType(Long userId, Long id, Type type) {
        return likesRepo.findByUserIdAndPartyIdAndType(userId, id, type);
    }

    public Optional<Likes> read(Long userId, Long party, Type type) {
        return likesRepo.findByUserIdAndPartyIdAndType(userId, party, type);
    }

    public boolean isExist(Optional<Likes> foundLike) {
        return foundLike.isPresent();
    }

    public void append(Likes likes) {
        likesRepo.save(likes);
    }

    public void addLikeCount(Likes likes) {
        if (likes.getType() == Type.POST) {
            Post post = postImplement.readPost(likes.getPartyId());
            post.increaseLike();
        }
        else if (likes.getType() == Type.COMMENT) {
            Comment comment = commentImplement.readById(likes.getPartyId());
            comment.increaseLike();
        }
        else if (likes.getType() == Type.RECOMMENT) {
            ReComment reComment = reCommentImplement.readById(likes.getPartyId());
            reComment.increaseLike();
        }
    }

    public void remove(Likes likes) {
        likesRepo.delete(likes);
    }

    public void decreaseLikeCount(Likes likes) {
        if (likes.getType() == Type.POST) {
            Post post = postImplement.readPost(likes.getPartyId());
            post.decreaseLike();
        }
        else if (likes.getType() == Type.COMMENT) {
            Comment comment = commentImplement.readById(likes.getPartyId());
            comment.decreaseLike();
        }
        else if (likes.getType() == Type.RECOMMENT) {
            ReComment reComment = reCommentImplement.readById(likes.getPartyId());
            reComment.decreaseLike();
        }
    }
}
