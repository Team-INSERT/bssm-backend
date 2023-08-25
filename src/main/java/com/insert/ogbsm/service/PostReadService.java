package com.insert.ogbsm.service;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.presentation.post.dto.PostResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
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
