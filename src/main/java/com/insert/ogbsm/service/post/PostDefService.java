package com.insert.ogbsm.service.post;

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

    public void delete(Long id) {
       //TODO 자기가 쓴 글인지 확인
        postRepo.deleteById(id);
    }
}
