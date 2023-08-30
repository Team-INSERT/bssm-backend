package com.insert.ogbsm.domain.bamboo.exception;

import com.insert.ogbsm.global.error.exception.BsmException;
import com.insert.ogbsm.global.error.exception.ErrorCode;

public class BambooAlreadyAllowed extends BsmException {

    public static final BambooAlreadyAllowed EXCEPTION = new BambooAlreadyAllowed(ErrorCode.BAMBOO_ALREADY_ALLOWED);

    public BambooAlreadyAllowed(ErrorCode errorCode) {
        super(errorCode);
    }
}
