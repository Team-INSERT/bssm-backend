package com.insert.ogbsm.presentation.comment.dto;

import java.util.List;

public record PageReCommentRes(List<ReCommentResDto> reComments, int totalPage) {
}
