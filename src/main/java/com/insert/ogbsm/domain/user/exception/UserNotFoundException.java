package com.insert.ogbsm.domain.user.exception;

import com.insert.ogbsm.global.error.exception.BsmException;
import com.insert.ogbsm.global.error.exception.ErrorCode;

public class UserNotFoundException extends BsmException {

    public static final UserNotFoundException EXCEPTION = new UserNotFoundException(ErrorCode.USER_NOT_FOUND);

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
