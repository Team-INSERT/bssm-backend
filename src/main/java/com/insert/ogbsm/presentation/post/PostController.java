package com.insert.ogbsm.presentation.post;

import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.presentation.post.dto.PostReqDto;
import com.insert.ogbsm.presentation.post.dto.PostResDto;
import com.insert.ogbsm.service.PostDefService;
import com.insert.ogbsm.service.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostDefService postDefService;
    private final PostReadService postReadService;

    @MutationMapping
    public PostResDto create(@Argument(name = "input") PostReqDto postReqDto) {
        return new PostResDto(
                postDefService.create(postReqDto.entityToBeCreated())
        );
    }

    @MutationMapping
    public PostResDto update(@Argument(name = "input") PostReqDto postReqDto) {
        return new PostResDto(
                postDefService.update(postReqDto.entityToBeUpdated())
        );
    }

    @QueryMapping
    public PostResDto readOne(@Argument Long id) {
        return new PostResDto(postReadService.readOne(id));
    }

    @QueryMapping
    public List<PostResDto> readByCategory(@Argument Category category) {
        return postReadService.readByCategory(category)
                .stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postDefService.delete(id);
    }
}
