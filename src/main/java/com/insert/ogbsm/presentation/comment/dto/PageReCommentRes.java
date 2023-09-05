package com.insert.ogbsm.presentation.comment.dto;

import java.util.List;

public record PageReCommentRes(List<ReCommentRes> reComments, int totalPage) {
}
