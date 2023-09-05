package com.insert.ogbsm.presentation.comment.dto;

import java.util.List;

public record PageCommentResDto(List<CommentRes> comments, int totalPage) {
}
