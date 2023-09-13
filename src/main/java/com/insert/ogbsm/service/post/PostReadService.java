package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.presentation.post.dto.PostLikeRes;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostReadService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final LikesRepo likesRepo;

    public PostLikeRes readOne(Long id, Long userId) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't Read An Entity"));

        User user = userRepo.findById(post.getWriterId())
                .orElseThrow(() -> new EntityNotFoundException("Post Writer Not Found"));

        boolean doesLike = doesLikePost(userId, post);

        return new PostLikeRes(post, user, doesLike);
    }

    private boolean doesLikePost(Long userId, Post post) {
        if (userId != null) {
            Optional<Likes> likes = likesRepo.findByUserIdAndPartyIdAndType(userId, post.getId(), Type.POST);
            return likes.isPresent();
        }

        return false;
    }

    public Pagination<List<PostRes>> readByCategory(Category category, Pageable pageable) {

        Page<Post> pagePost = postRepo.findByCategory(category, pageable);
        List<PostRes> collect = pagePost
                .stream()
                .map(post -> new PostRes(post, userRepo.findById(post.getWriterId())
                        .orElseThrow(
                                () -> new EntityNotFoundException("Post Read Category User Not Found"))))
                .collect(Collectors.toList());

        return new Pagination<>(collect, pagePost.getTotalPages(), pageable.getPageNumber());
    }
}
