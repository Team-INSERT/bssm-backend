package com.insert.ogbsm.service;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostDefService {

    private final PostRepo postRepo;

    public Post create(Post post) {
        return postRepo.save(post);
    }

    public Post update(Post post) {
        Post updatablePost = postRepo.findById(post.getId())
                .orElseThrow(() -> new EntityNotFoundException("No Updatable Post"));

        updatablePost.update(post);

        return updatablePost;
    }
}
