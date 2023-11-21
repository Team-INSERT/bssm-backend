package com.insert.ogbsm.service.bamboo.implement;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.domain.bamboo.repo.AllowedBambooRepo;
import com.insert.ogbsm.domain.bamboo.repo.BambooRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.bamboo.dto.BambooRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BambooImplement {
    private final BambooRepo bambooRepo;
    private final AllowedBambooRepo allowedBambooRepo;

    public List<BambooRes> readNotAllowed() {
        return bambooRepo.findAllByIsAllow(false)
                .stream()
                .map(BambooRes::new)
                .toList();
    }

    public Bamboo read(Long bambooId) {
        return bambooRepo.findById(bambooId)
                .orElseThrow(() -> new BsmException(ErrorCode.BAMBOO_ALREADY_ALLOWED));
    }

    public AllowedBambooRes updateBambooAllowed(Bamboo bamboo) {
        bamboo.setIsAllow();
        return new AllowedBambooRes(
                allowedBambooRepo.save(
                        AllowedBamboo
                                .builder()
                                .allowedAdminId(SecurityUtil.getCurrentUserWithLogin().getId())
                                .bamboo(bamboo)
                                .build())
        );
    }

    public void remove(Bamboo bamboo) {
        if (bamboo.getIsAllow()) {
            allowedBambooRepo.delete(
                    allowedBambooRepo.findByBamboo(bamboo)
                            .orElseThrow(() -> new BsmException(ErrorCode.BAMBOO_NOT_FOUND))
            );
        }
        bambooRepo.deleteById(bamboo.getId());
    }

    public Slice<AllowedBambooRes> readAllowed(Pageable pageable) {
        return allowedBambooRepo.findAllByCreateAtDesc(pageable)
                .map(AllowedBambooRes::new);
    }

    public void save(Bamboo bamboo) {
        bambooRepo.save(bamboo);
    }

    public AllowedBambooRes readMostRecentAllowed() {
        List<AllowedBamboo> all = allowedBambooRepo.findAll();
        return new AllowedBambooRes(all.get(all.size() - 1));
    }
}
