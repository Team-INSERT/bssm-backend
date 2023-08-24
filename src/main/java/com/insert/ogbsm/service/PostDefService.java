package com.insert.ogbsm.service;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostDefService {

    private final PostRepo postRepo;

    public Post create(Post post) {
        return postRepo.save(post);
    }

    public Post update(Post post) {
        return postRepo.save(post);
    }
}
