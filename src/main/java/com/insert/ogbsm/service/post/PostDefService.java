package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.service.post.inter.DefService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostDefService extends DefService<Post, PostRepo> {

    public PostDefService(PostRepo postRepo) {
        super(postRepo);
    }
}
