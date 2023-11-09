package com.insert.ogbsm.presentation.comment;

import com.insert.ogbsm.domain.comment.Comment;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.comment.dto.CommentReq;
import com.insert.ogbsm.presentation.comment.dto.CommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.service.comment.business.CommentBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentBusiness commentBusiness;

    @PostMapping("/{postId}")
    public void create(@RequestBody CommentReq dto, @PathVariable Long postId) {
        User user = SecurityUtil.getCurrentUserWithLogin();
        commentBusiness.create(new Comment(dto.detail(), postId, user.getId()));
    }

    @PutMapping()
    public void update(@RequestBody CommentReq dto) {
        User user = SecurityUtil.getCurrentUserWithLogin();
        commentBusiness.update(dto, user.getId());
    }

    @GetMapping("/{postId}")
    public Pagination<List<CommentRes>> readByPostId(@PathVariable Long postId, @PageableDefault Pageable pageable) {
        Long userId = SecurityUtil.getCurrentUserIdWithoutLogin();
        return commentBusiness.readComments(postId, userId, pageable);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        User user = SecurityUtil.getCurrentUserWithLogin();
        commentBusiness.delete(commentId, user.getId());
    }
}
