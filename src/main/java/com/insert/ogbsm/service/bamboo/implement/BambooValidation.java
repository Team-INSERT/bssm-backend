package com.insert.ogbsm.service.bamboo.implement;

import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BambooValidation {
    public void bambooShouldNotAllowed(Bamboo bamboo) {
        if(bamboo.getIsAllow()) {
            throw new BsmException(ErrorCode.BAMBOO_ALREADY_ALLOWED);
        }
    }
}
