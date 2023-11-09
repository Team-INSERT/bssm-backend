package com.insert.ogbsm.service.like.business;

import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.service.like.implement.LikeValidation;
import com.insert.ogbsm.service.like.implement.LikesImplement;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeDefBusiness {
    private final LikeValidation likeValidation;
    private final LikesImplement likesImplement;

    public boolean changeLikeStatus(Likes likes) {

        Optional<Likes> foundLike = likesImplement.read(likes.getUserId(), likes.getPartyId(), likes.getType());
        likeValidation.beforeCreating(likes);

        if (likesImplement.isExist(foundLike)) {
            likesImplement.append(likes);
            likesImplement.addLikeCount(likes);
            return true;
        }

        likesImplement.remove(likes);
        likesImplement.decreaseLikeCount(likes);
        return false;
    }
}
