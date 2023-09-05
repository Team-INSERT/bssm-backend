package com.insert.ogbsm.presentation.comment.dto;

import java.util.List;

public record PageCommentRes(List<CommentRes> comments, int totalPage) {
}
