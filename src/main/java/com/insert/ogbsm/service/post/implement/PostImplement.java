package com.insert.ogbsm.service.post.implement;

import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.user.implement.UserImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostImplement {
    private final PostRepo postRepo;
    private final UserImplement userImplement;
    private final LikesRepo likesRepo;

    public Post read(Long postId) {
        return postRepo.findById(postId)
                .orElseThrow(() -> new BsmException(ErrorCode.POST_NOT_FOUND));
    }

    public Post append(Post post) {
        return postRepo.save(post);
    }

    public void delete(Post post) {
        postRepo.delete(post);
    }

    public User readUser(Long userId) {
        return userImplement.readUser(userId);

    }

    public boolean doesLikePost(Long userId, Post post) {
        if (userId == null) {
            return false;
        }

        Optional<Likes> likes = likesRepo.findByUserIdAndPartyIdAndType(userId, post.getId(), Type.POST);
        return likes.isPresent();
    }

    public Page<Post> readByCategory(Category category, Pageable pageable) {
        return postRepo.findByCategory(category, pageable);
    }

    public Pagination<List<PostRes>> fillUser(Pageable pageable, Page<Post> pagePost) {
        List<PostRes> collect = pagePost
                .stream()
                .map(post -> new PostRes(post, readUser(post.getWriterId())))
                .collect(Collectors.toList());

        return new Pagination<>(collect, pagePost.getTotalPages(), pageable.getPageNumber());
    }

    public List<PostRes> readTop(int limit, Category category) {
        return postRepo.findByCategory(category).stream()
                .limit(limit)
                .map(post -> new PostRes(post, readUser(post.getWriterId())))
                .toList();
    }
}
