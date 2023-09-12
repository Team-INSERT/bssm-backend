package com.insert.ogbsm.service.bamboo;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.domain.bamboo.exception.BambooAlreadyAllowed;
import com.insert.ogbsm.domain.bamboo.repo.AllowedBambooRepo;
import com.insert.ogbsm.domain.bamboo.repo.BambooRepo;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.BambooRes;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BambooAdminService {

    private final BambooRepo bambooRepo;
    private final AllowedBambooRepo allowedBambooRepo;

    public List<BambooRes> findAllBamboo() {
        return bambooRepo.findAllByIsAllow(false)
                .stream()
                .map(BambooRes::new).toList();
    }

    public AllowedBambooRes allowBamboo(Long id, Long userId) {
        Bamboo bamboo = bambooRepo.findById(id).orElseThrow(EntityNotFoundException::new);

        if(bamboo.getIsAllow()) {
            throw BambooAlreadyAllowed.EXCEPTION;
        }

        bamboo.setIsAllow();
        return new AllowedBambooRes(
                allowedBambooRepo.save(AllowedBamboo.builder()
                        .allowedAdminId(userId)
                        .bamboo(bamboo)
                        .build())
        );
    }

    public Long deleteBamboo(Long id) {
        Bamboo bamboo = bambooRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        if (bamboo.getIsAllow()) {
            allowedBambooRepo.delete(
                    allowedBambooRepo.findByBamboo(bamboo)
                            .orElseThrow(EntityNotFoundException::new)
            );
        }
        bambooRepo.deleteById(id);
        return id;
    }

}
