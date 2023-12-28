package com.insert.ogbsm.presentation.comment;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.comment.dto.ReCommentReq;
import com.insert.ogbsm.presentation.comment.dto.ReCommentRes;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.service.comment.business.ReCommentBusiness;
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
    private final ReCommentBusiness reCommentBusiness;

    @PostMapping("/{commentId}")
    public void create(@RequestBody ReCommentReq reqDto, @PathVariable Long commentId) {
        Long userId = SecurityUtil.getCurrentUserIdWithLogin();
        reCommentBusiness.create(reqDto, commentId, userId);
    }

    @GetMapping("/{commentId}")
    public Pagination<List<ReCommentRes>> read(@PathVariable Long commentId, @PageableDefault Pageable pageable) {
        Long userId = SecurityUtil.getCurrentUserIdWithoutLogin();
        return reCommentBusiness.read(commentId, pageable, userId);
    }

    @PutMapping()
    public void update(@RequestBody ReCommentReq reqDto) {
        Long userId = SecurityUtil.getCurrentUserIdWithLogin();
        reCommentBusiness.update(reqDto, userId);
    }

    @DeleteMapping("/{reCommentId}")
    public void delete(@PathVariable Long reCommentId) {
        Long userId = SecurityUtil.getCurrentUserIdWithLogin();
        reCommentBusiness.delete(reCommentId, userId);
    }
}
