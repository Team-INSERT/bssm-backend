package com.insert.ogbsm.presentation.post;

import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.global.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.presentation.post.dto.PostReq;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.post.PostDefService;
import com.insert.ogbsm.service.post.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public PostRes readOne(@Argument Long id) {
        return postReadService.readOne(id);
    }

    @QueryMapping(name = "readByCategory")
    public Pagination<List<PostRes>> readByCategory(@Argument Category category, @Argument int page, @Argument int size) {
        return postReadService.readByCategory(category, PageRequest.of(page, size));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postDefService.delete(id);
    }
}
