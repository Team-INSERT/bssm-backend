package com.insert.ogbsm.service.post;

import com.insert.ogbsm.domain.common.OrderType;
import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.repo.LikesRepo;
import com.insert.ogbsm.domain.like.type.Type;
import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.domain.post.repo.PostWrapper;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.UserWrapper;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.presentation.post.dto.PostLikeRes;
import com.insert.ogbsm.presentation.post.dto.PostRes;
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
    private final PostWrapper postWrapper;
    private final UserWrapper userWrapper;
    private final LikesRepo likesRepo;

    public PostLikeRes readOne(Long id, Long userId) {
        Post post = postWrapper.getPost(id);

        User user = userWrapper.getUser(post.getWriterId());

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

    public Pagination<List<PostRes>> readByCategory(Category category, Pageable pageable, OrderType orderType) {

        if (orderType == OrderType.LIKE) {
            return changeToPagination(postRepo.findByCategoryOrderByLikeCountDescCreatedAtDesc(category, pageable), pageable);
        } else if (orderType == OrderType.RECENT) {
            return changeToPagination(postRepo.findByCategoryOrderByCreatedAtDesc(category, pageable), pageable);
        }

        throw new BsmException(ErrorCode.NO_ORDERTYPE_EXCEPTION);
    }

    private Pagination<List<PostRes>> changeToPagination(Page<Post> postRepo, Pageable pageable) {

        List<PostRes> collect = postRepo
                .stream()
                .map(post -> new PostRes(post, userWrapper.getUser(post.getWriterId())))
                .collect(Collectors.toList());

        return new Pagination<>(collect, postRepo.getTotalPages(), pageable.getPageNumber());
    }
}
