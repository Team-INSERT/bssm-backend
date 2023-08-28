package com.insert.ogbsm.global.jwt.exception;

import com.insert.ogbsm.global.error.exception.BsmException;
import com.insert.ogbsm.global.error.exception.ErrorCode;


public class ExpiredJwtException extends BsmException {
    public final static ExpiredJwtException EXCEPTION = new ExpiredJwtException(ErrorCode.EXPIRED_JWT);

    public ExpiredJwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
