package com.insert.ogbsm.presentation.post;

import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.global.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.post.dto.PostReqDto;
import com.insert.ogbsm.presentation.post.dto.PostResDto;
import com.insert.ogbsm.service.post.PostDefService;
import com.insert.ogbsm.service.post.PostReadService;
import lombok.RequiredArgsConstructor;
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
    public PostResDto create(@Argument(name = "input") PostReqDto postReqDto) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return postDefService.create(postReqDto, user);
    }

    @MutationMapping
    public PostResDto update(@Argument(name = "input") PostReqDto postReqDto) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return postDefService.update(postReqDto, user);
    }

    @QueryMapping
    public PostResDto readOne(@Argument Long id) {
        return postReadService.readOne(id);
    }

    @QueryMapping
    public List<PostResDto> readByCategory(@Argument Category category) {
        return postReadService.readByCategory(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postDefService.delete(id);
    }
}
