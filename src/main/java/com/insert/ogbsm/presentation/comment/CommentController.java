package com.insert.ogbsm.presentation.comment;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.global.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.comment.dto.CommentReqDto;
import com.insert.ogbsm.presentation.comment.dto.CommentResDto;
import com.insert.ogbsm.service.comment.CommentDefService;
import com.insert.ogbsm.service.comment.CommentReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentDefService commentDefService;
    private final CommentReadService commentReadService;

    @PostMapping("/{postId}")
    public void create(@RequestBody CommentReqDto dto, @PathVariable Long postId) {
        User user = SecurityUtil.getCurrentUserWithLogin();
        commentDefService.create(dto, postId, user.getId());
    }

    @PutMapping()
    public void update(@RequestBody CommentReqDto dto) {
        User user = SecurityUtil.getCurrentUserWithLogin();
        commentDefService.update(dto, user.getId());
    }

    @GetMapping("/{postId}")
    public List<CommentResDto> readByPostId(@PathVariable Long postId, @PageableDefault Pageable pageable) {
        return commentReadService.readComments(postId, pageable);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        User user = SecurityUtil.getCurrentUserWithLogin();
        commentDefService.delete(commentId, user.getId());
    }
}
