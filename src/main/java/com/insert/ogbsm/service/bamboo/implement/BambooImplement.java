package com.insert.ogbsm.service.bamboo.implement;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BambooImplement {
    private final EntityManager em;

    public List<Bamboo> readNotAllowed() {
        return em
                .createQuery("select b from Bamboo b where b.isAllow = false", Bamboo.class)
                .getResultList();
    }

    public Bamboo read(Long bambooId) {
        Bamboo bamboo = em.find(Bamboo.class, bambooId);

        if (bamboo == null) {
            throw new BsmException(ErrorCode.BAMBOO_NOT_FOUND);
        }

        return bamboo;
    }

    public AllowedBamboo updateBambooAllowed(Bamboo bamboo, User admin) {
        bamboo.setIsAllow();

        AllowedBamboo allowed = AllowedBamboo
                .builder()
                .allowedAdminId(admin.getId())
                .bamboo(bamboo)
                .build();

        em.persist(allowed);

        return allowed;
    }

    public void remove(Bamboo bamboo) {
        if (bamboo.getIsAllow()) {
            List<AllowedBamboo> allowedBamboo = em.createQuery("select a from AllowedBamboo a where a.bamboo = :bamboo", AllowedBamboo.class)
                    .setParameter("bamboo", bamboo)
                    .getResultList();

            if (allowedBamboo.isEmpty()) {
                throw new BsmException(ErrorCode.BAMBOO_NOT_FOUND);
            }

            em.remove(
                    allowedBamboo.get(0)
            );
        }
        em.remove(bamboo);
    }

    public Page<AllowedBamboo> readAllowed(Pageable pageable) {
        List<AllowedBamboo> allowedBamboos = em.createQuery("select a from AllowedBamboo a", AllowedBamboo.class)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults((pageable.getPageNumber() + 1) * pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(allowedBamboos);
    }

    public void save(Bamboo bamboo) {
        em.persist(bamboo);
    }

    public AllowedBamboo readMostRecentAllowed() {
        List<AllowedBamboo> all = em
                .createQuery("select a from AllowedBamboo a", AllowedBamboo.class)
                .getResultList();

        return all.get(all.size() - 1);
    }
}
