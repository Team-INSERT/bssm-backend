package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public PostRes readOne(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't Read An Entity"));

        User user = userRepo.findById(post.getWriterId())
                .orElseThrow(() -> new EntityNotFoundException("Post Writer Not Found"));

        return new PostRes(post, user);
    }

    public List<PostRes> readByCategory(Category category) {
        return postRepo.findByCategory(category)
                .stream()
                .map(post -> new PostRes(post, userRepo.findById(post.getWriterId())
                        .orElseThrow(
                                () -> new EntityNotFoundException("Post Read Category User Not Found"))))
                .collect(Collectors.toList());
    }
}
