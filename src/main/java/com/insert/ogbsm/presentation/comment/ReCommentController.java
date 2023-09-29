package com.insert.ogbsm.presentation.comment;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.comment.dto.ReCommentReq;
import com.insert.ogbsm.presentation.comment.dto.ReCommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.service.comment.ReCommentDefService;
import com.insert.ogbsm.service.comment.ReCommentReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.insert.ogbsm.presentation.comment.dto.ReCommentRes.*;

@RestController
@RequestMapping("/recomment")
@RequiredArgsConstructor
public class ReCommentController {
    private final ReCommentDefService reCommentDefService;
    private final ReCommentReadService reCommentReadService;

    @PostMapping("/{commentId}")
    public ReCommentDefRes create(@RequestBody ReCommentReq reqDto, @PathVariable Long commentId) {
        Long userId = SecurityUtil.getCurrentUserIdWithLogin();
        return reCommentDefService.create(reqDto, commentId, userId);
    }

    @GetMapping("/{commentId}")
    public Pagination<List<ReCommentRes>> read(@PathVariable Long commentId, @PageableDefault Pageable pageable) {
        Long userId = SecurityUtil.getCurrentUserIdWithoutLogin();
        return reCommentReadService.read(commentId, pageable, userId);
    }

    @PutMapping()
    public ReCommentDefRes update(@RequestBody ReCommentReq reqDto) {
        Long userId = SecurityUtil.getCurrentUserIdWithLogin();
        return reCommentDefService.update(reqDto, userId);
    }

    @DeleteMapping("/{reCommentId}")
    public ReCommentDefRes delete(@PathVariable Long reCommentId) {
        Long userId = SecurityUtil.getCurrentUserIdWithLogin();
        return reCommentDefService.delete(reCommentId, userId);
    }
}
