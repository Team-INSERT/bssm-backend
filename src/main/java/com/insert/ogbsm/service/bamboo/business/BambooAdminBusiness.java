package com.insert.ogbsm.service.bamboo.business;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.BambooRes;
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
    public List<BambooRes> findAllBamboo() {
        return bambooImplement.readNotAllowed();
    }

    public AllowedBambooRes allowBamboo(Long bambooId) {
        Bamboo bamboo = bambooImplement.read(bambooId);
        bambooValidation.bambooShouldNotAllowed(bamboo);
        return bambooImplement.updateBambooAllowed(bamboo);
    }

    public Long deleteBamboo(Long bambooId) {
        Bamboo bamboo = bambooImplement.read(bambooId);
        bambooImplement.remove(bamboo);
        return bambooId;
    }
}
