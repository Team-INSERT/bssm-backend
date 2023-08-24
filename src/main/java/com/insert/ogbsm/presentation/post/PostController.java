package com.insert.ogbsm.presentation.post;

import com.insert.ogbsm.presentation.post.dto.PostReqDto;
import com.insert.ogbsm.presentation.post.dto.PostResDto;
import com.insert.ogbsm.service.PostDefService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostDefService postDefService;

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
}
