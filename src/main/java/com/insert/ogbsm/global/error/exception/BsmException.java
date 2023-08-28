package com.insert.ogbsm.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BsmException extends RuntimeException {
    private final ErrorCode errorCode;
}
