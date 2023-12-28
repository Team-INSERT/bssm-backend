package com.insert.ogbsm.service.bamboo.business;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.service.bamboo.implement.BambooImplement;
import com.insert.ogbsm.service.bamboo.implement.BambooValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BambooAdminBusiness {

    private final BambooImplement bambooImplement;
    private final BambooValidation bambooValidation;

    @Transactional(readOnly = true)
    public List<Bamboo> findAllBamboo() {
        return bambooImplement.readNotAllowed();
    }

    public AllowedBamboo allowBamboo(Long bambooId, User admin) {
        Bamboo bamboo = bambooImplement.read(bambooId);
        bambooValidation.bambooShouldNotAllowed(bamboo);
        return bambooImplement.updateBambooAllowed(bamboo, admin);
    }

    public Long deleteBamboo(Long bambooId) {
        Bamboo bamboo = bambooImplement.read(bambooId);
        bambooImplement.remove(bamboo);
        return bambooId;
    }
}
