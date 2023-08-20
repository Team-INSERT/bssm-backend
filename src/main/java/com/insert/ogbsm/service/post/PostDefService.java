package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostDefService {

    private final PostRepo postRepo;

    public Post create(Post post) {
        return postRepo.save(post);
    }

    public Post update(Post post) {
        Post updatablePost = postRepo.findById(post.getId())
                .orElseThrow((() -> new EntityNotFoundException("Updatable Post Not Found"))); // ToDo

        updatablePost.update(post.getTitle(), post.getContent());

        return updatablePost;
    }
}
