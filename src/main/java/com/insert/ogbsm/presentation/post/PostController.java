package com.insert.ogbsm.presentation.post;

import com.insert.ogbsm.domain.common.OrderType;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.presentation.post.dto.PostDeleteRes;
import com.insert.ogbsm.presentation.post.dto.PostLikeRes;
import com.insert.ogbsm.presentation.post.dto.PostReq;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.post.PostDefService;
import com.insert.ogbsm.service.post.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostDefService postDefService;
    private final PostReadService postReadService;

    @MutationMapping
    public PostRes create(@Argument(name = "input") PostReq postReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return postDefService.create(postReq, user);
    }

    @MutationMapping
    public PostRes update(@Argument(name = "input") PostReq postReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return postDefService.update(postReq, user);
    }

    @QueryMapping
    public PostLikeRes readOne(@Argument Long id) {
        Long userId = SecurityUtil.getCurrentUserIdWithoutLogin();
        return postReadService.readOne(id, userId);
    }

    @QueryMapping
    public Pagination<List<PostRes>> readByCategory(@Argument Category category, @Argument int page, @Argument int size, @Argument OrderType orderType) {
        return postReadService.readByCategory(category, PageRequest.of(page, size), orderType);
    }

    @MutationMapping
    public PostDeleteRes delete(@Argument Long id) {
        Long userId = SecurityUtil.getCurrentUserIdWithLogin();
        return postDefService.delete(id, userId);
    }

    @MutationMapping
    public PostRes updateLostFoundUser(@Argument Long postId, @Argument Long foundUserId) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return postDefService.updateLostAndFound(postId, foundUserId, user);
    }
}
