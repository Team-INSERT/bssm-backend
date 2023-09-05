package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.post.dto.PostReqDto;
import com.insert.ogbsm.presentation.post.dto.PostResDto;
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

    public PostResDto create(PostReqDto postReqDto, User user) {
        Post post = postRepo.save(postReqDto.entityToBeCreated(user.getId()));

        return new PostResDto(post, user);
    }

    public PostResDto update(PostReqDto reqDto, User user) {
        Post updatablePost = postRepo.findById(reqDto.id())
                .orElseThrow(() -> new EntityNotFoundException("No Updatable Post"));

        userValidation.checkSameUser(updatablePost.getWriterId(), user.getId());

        Post post = reqDto.entityToBeUpdated(user.getId());
        updatablePost.update(post);

        return new PostResDto(post, user);
    }

    public void delete(Long id) {

        postRepo.deleteById(id);
    }
}
