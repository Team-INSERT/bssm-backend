package com.insert.ogbsm.domain.user.exception;

import com.insert.ogbsm.global.error.exception.BsmException;
import com.insert.ogbsm.global.error.exception.ErrorCode;

public class UserNotLoginException extends BsmException {

    public static final UserNotFoundException EXCEPTION = new UserNotFoundException(ErrorCode.USER_NOT_LOGIN);

    public UserNotLoginException(ErrorCode errorCode) {
        super(errorCode);
    }
}
