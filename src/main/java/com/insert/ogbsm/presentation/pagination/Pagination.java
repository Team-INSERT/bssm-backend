package com.insert.ogbsm.presentation.pagination;

public record Pagination<T>(T entity, int totalPage, int currentPage) {
}
