package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.UserWrapper;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.post.dto.PostDeleteRes;
import com.insert.ogbsm.presentation.post.dto.PostReq;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostDefService {

    private final PostRepo postRepo;
    private final UserWrapper userWrapper;
    private final UserValidation userValidation;

    public PostRes create(PostReq postReq, User user) {
        Post post = postRepo.save(postReq.entityToBeCreated(user.getId()));

        return new PostRes(post, user);
    }

    public PostRes update(PostReq reqDto, User user) {
        Post updatablePost = postRepo.findById(reqDto.id())
                .orElseThrow(() -> new BsmException(ErrorCode.POST_NOT_FOUND));

        userValidation.checkSameUser(updatablePost.getWriterId(), user.getId());

        Post post = reqDto.entityToBeUpdated(user.getId());
        updatablePost.update(post);

        return new PostRes(post, user);
    }

    public PostDeleteRes delete(Long id, Long userId) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new BsmException(ErrorCode.USER_NOT_FOUND));

        userValidation.checkSameUser(post.getWriterId(), userId);

        postRepo.deleteById(id);

        return new PostDeleteRes(post.getId());
    }

    public PostRes updateFoundUser(Long postId, Long foundUserId, User user) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new BsmException(ErrorCode.POST_NOT_FOUND));

        userValidation.checkSameUser(post.getWriterId(), user.getId());

        if (post.getCategory() != Category.LOST && post.getCategory() != Category.FOUND) {
            throw new BsmException(ErrorCode.POST_TYPE_WEIRD);
        }

        User foundUser = userWrapper.getUser(foundUserId);

        post.getLostFound().updateFoundUserId(user.getId());

        return new PostRes(post, user, foundUser);
    }
}
