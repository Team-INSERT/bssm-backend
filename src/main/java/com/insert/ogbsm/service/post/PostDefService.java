package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.exception.UserNotFoundException;
import com.insert.ogbsm.global.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.post.dto.PostDeleteRes;
import com.insert.ogbsm.presentation.post.dto.PostReq;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.validation.UserValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostDefService {

    private final PostRepo postRepo;
    private final UserValidation userValidation;

    public PostRes create(PostReq postReq, User user) {
        Post post = postRepo.save(postReq.entityToBeCreated(user.getId()));

        return new PostRes(post, user);
    }

    public PostRes update(PostReq reqDto, User user) {
        Post updatablePost = postRepo.findById(reqDto.id())
                .orElseThrow(() -> new EntityNotFoundException("No Updatable Post"));

        userValidation.checkSameUser(updatablePost.getWriterId(), user.getId());

        Post post = reqDto.entityToBeUpdated(user.getId());
        updatablePost.update(post);

        return new PostRes(post, user);
    }

    public PostDeleteRes delete(Long id, Long userId) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND));

        userValidation.checkSameUser(post.getWriterId(), userId);

        postRepo.deleteById(id);

        return new PostDeleteRes(post.getId());
    }
}
