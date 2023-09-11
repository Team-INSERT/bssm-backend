package com.insert.ogbsm.presentation.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pagination<T> {
    private T entity;
    private int totalPage;
    private int currentPage;
}
