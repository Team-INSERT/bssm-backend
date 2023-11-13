package com.insert.ogbsm.service.bamboo.implement;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.domain.bamboo.repo.AllowedBambooRepo;
import com.insert.ogbsm.domain.bamboo.repo.BambooRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BambooImplement {
    private final BambooRepo bambooRepo;
    private final AllowedBambooRepo allowedBambooRepo;

    public List<Bamboo> readNotAllowed() {
        return bambooRepo.findAllByIsAllow(false);
    }

    public Bamboo read(Long bambooId) {
        return bambooRepo.findById(bambooId)
                .orElseThrow(() -> new BsmException(ErrorCode.BAMBOO_NOT_FOUND));
    }

    public AllowedBamboo updateBambooAllowed(Bamboo bamboo, User admin) {
        bamboo.setIsAllow();
        return allowedBambooRepo.save(
                AllowedBamboo
                        .builder()
                        .allowedAdminId(admin.getId())
                        .bamboo(bamboo)
                        .build());
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

    public Page<AllowedBamboo> readAllowed(Pageable pageable) {
        return allowedBambooRepo.findAll(pageable);
    }

    public void save(Bamboo bamboo) {
        bambooRepo.save(bamboo);
    }

    public AllowedBamboo readMostRecentAllowed() {
        List<AllowedBamboo> all = allowedBambooRepo.findAll();
        return all.get(all.size() - 1);
    }
}
