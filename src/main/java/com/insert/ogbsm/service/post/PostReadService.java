package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostReadService {

    private final PostRepo postRepo;

    public List<Post> getPostDayDesc() {
        return postRepo.findAllCreateTimeDesc();
    }

    public Post getPostById(Long id) {
        return postRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("get Post By Id Not Found"));
    }
}
