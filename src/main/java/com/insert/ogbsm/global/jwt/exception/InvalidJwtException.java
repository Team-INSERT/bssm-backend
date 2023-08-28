package com.insert.ogbsm.global.jwt.exception;

import com.insert.ogbsm.global.error.exception.BsmException;
import com.insert.ogbsm.global.error.exception.ErrorCode;


public class InvalidJwtException extends BsmException {

    public static InvalidJwtException EXCEPTION = new InvalidJwtException(ErrorCode.INVALID_TOKEN);

    public InvalidJwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
