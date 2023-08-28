package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {

    private final PostRepo postRepo;

    public Post readOne(Long id) {
        return postRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't Read An Entity"));
    }

    public List<Post> readByCategory(Category category) {
        return postRepo.findByCategory(category);
    }
}
