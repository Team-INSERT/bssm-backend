package com.insert.ogbsm.domain.auth.exception;

import com.insert.ogbsm.domain.user.exception.UserNotFoundException;
import com.insert.ogbsm.global.error.exception.BsmException;
import com.insert.ogbsm.global.error.exception.ErrorCode;

public class InvalidClientException extends BsmException {
    public static final UserNotFoundException EXCEPTION = new UserNotFoundException(ErrorCode.BSM_AUTH_INVALID_CLIENT);

    public InvalidClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
