package com.insert.ogbsm.service.validation;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserValidation {

    public void checkSameUser(Long id1, Long id2) {
        if (!Objects.equals(id1, id2)) {
            throw new AccessDeniedException("Your not a same user");
        }
    }

}
