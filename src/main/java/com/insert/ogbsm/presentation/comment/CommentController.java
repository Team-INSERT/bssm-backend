package com.insert.ogbsm.presentation.comment;

import com.insert.ogbsm.presentation.comment.dto.CommentReqDto;
import com.insert.ogbsm.presentation.comment.dto.CommentResDto;
import com.insert.ogbsm.service.comment.CommentDefService;
import com.insert.ogbsm.service.comment.CommentReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentDefService commentDefService;
    private final CommentReadService commentReadService;

    @PostMapping("/{postId}")
    public void create(@RequestBody CommentReqDto dto, @PathVariable Long postId) {
        // TODO get User Login
        commentDefService.createComment(dto, postId, 10L);
    }

    @PutMapping()
    public void update(@RequestBody CommentReqDto dto) {
        //TODO get User Login
        commentDefService.updateComment(dto, 10L);
    }

    @GetMapping("/{postId}")
    public List<CommentResDto> readByPostId(@PathVariable Long postId, @PageableDefault Pageable pageable) {
        System.out.println("들어는 오는 건가");
        List<CommentResDto> 히히 = commentReadService.readComments(postId, pageable);

        return 히히;
    }
}
