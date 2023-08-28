package com.insert.ogbsm.global.jwt.exception;

import com.insert.ogbsm.global.error.exception.BsmException;
import com.insert.ogbsm.global.error.exception.ErrorCode;

public class RefreshTokenExpiredException extends BsmException {

    public final static RefreshTokenExpiredException EXCEPTION = new RefreshTokenExpiredException(ErrorCode.REFRESH_TOKEN_EXPIRED);

    public RefreshTokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
