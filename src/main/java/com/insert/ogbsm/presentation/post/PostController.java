package com.insert.ogbsm.presentation.post;

import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.presentation.post.dto.PostDeleteRes;
import com.insert.ogbsm.presentation.post.dto.PostLikeRes;
import com.insert.ogbsm.presentation.post.dto.PostReq;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.post.business.PostBusiness;
import lombok.RequiredArgsConstructor;
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
    private final PostBusiness postBusiness;

    @MutationMapping
    public PostRes create(@Argument(name = "input") PostReq postReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return postBusiness.create(postReq.entityToBeCreated(user.getId()), user);
    }

    @MutationMapping
    public PostRes update(@Argument(name = "input") PostReq postReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return postBusiness.update(postReq.entityToBeUpdated(user.getId()), user);
    }

    @QueryMapping
    public PostLikeRes readOne(@Argument Long id) {
        Long userId = SecurityUtil.getCurrentUserIdWithoutLogin();
        return postBusiness.readOne(id, userId);
    }

    @QueryMapping
    public Pagination<List<PostRes>> readByCategory(@Argument Category category, @Argument int page, @Argument int size) {
        return postBusiness.readByCategory(category, PageRequest.of(page, size));
    }

    @MutationMapping
    public void delete(@Argument Long id) {
        Long userId = SecurityUtil.getCurrentUserIdWithLogin();
        postBusiness.delete(id, userId);
    }

    @MutationMapping
    public PostRes updateLostFoundUser(@Argument Long postId, @Argument Long foundUserId) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return postBusiness.updateLostAndFound(postId, foundUserId, user);
    }
}
