package com.insert.ogbsm.service.validation;

import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserValidation {

    public void checkSameUser(Long id1, Long id2) {
        if (!Objects.equals(id1, id2)) {
            throw new BsmException(ErrorCode.NOT_SAME_USER);
        }
    }

}
