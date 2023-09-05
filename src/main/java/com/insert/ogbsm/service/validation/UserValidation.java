package com.insert.ogbsm.service.validation;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserValidation {

    public void checkSameUser(Long id1, Long id2) {
        if (!Objects.equals(id1, id2)) {
            throw new EntityNotFoundException("Your not a same user");
        }
    }

}
