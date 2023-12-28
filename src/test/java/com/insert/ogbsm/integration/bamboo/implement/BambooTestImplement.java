package com.insert.ogbsm.integration.bamboo.implement;

import com.insert.ogbsm.common.IntegrationTest;
import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.service.bamboo.business.BambooAdminBusiness;
import com.insert.ogbsm.service.bamboo.business.BambooBusiness;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class BambooTestImplement extends IntegrationTest {
    @Autowired
    private BambooBusiness bambooBusiness;

    @Autowired
    private EntityManager em;

    @Autowired
    private BambooAdminBusiness bambooAdminBusiness;

    protected Bamboo 허가되지_않은_bamboo() {
        return Bamboo.builder()
                .content("허가되지 않은 bamboo입니다.")
                .userId(1L)
                .isAllow(false)
                .build();
    }

    protected AllowedBamboo 허가된_bamboo() {
        Bamboo 허가된_bamboo = Bamboo.builder()
                .content("허가된 bamboo입니다.")
                .userId(1L)
                .isAllow(true)
                .build();

        return AllowedBamboo.builder()
                .bamboo(허가된_bamboo)
                .allowedAdminId(0L)
                .build();
    }

    protected Bamboo bamboo는_allowed이지만_허가된_bamboo가_없는_bamboo() {
        return Bamboo.builder()
                .content("특별한 이유로 AllowedBamboo가 삭제된 케이스입니다.")
                .userId(1L)
                .isAllow(true)
                .build();
    }

    protected void 허가된_bamboo_전체_저장(List<AllowedBamboo> 저장할_bamboo) {
        for (AllowedBamboo allowedBamboo : 저장할_bamboo) {
            em.persist(allowedBamboo);
        }
    }

    protected void 허가되지_않은_bamboo_전체_저장(List<Bamboo> 저장할_bamboo) {
        for (Bamboo bamboo : 저장할_bamboo) {
            em.persist(bamboo);
        }
    }

    protected Page<AllowedBamboo> 허가된_bamboo_전체_조회하기(int 읽을_페이지, int 읽을_개수) {
        return bambooBusiness.findAllAllowedBamboo(PageRequest.of(읽을_페이지, 읽을_개수));
    }

    protected void 저장하기(Bamboo 생성_요청_bamboo) {
        bambooBusiness.createBamboo(생성_요청_bamboo);
    }

    protected void 저장_결과를_검증(Bamboo 생성_요청_bamboo) {
        Bamboo 찾은_bamboo = em.find(Bamboo.class, 생성_요청_bamboo.getId());

        if (찾은_bamboo == null) {
            fail("조회한 bamboo가 비어있습니다.");
        }

        if (!찾은_bamboo.equals(생성_요청_bamboo)) {
            fail("찾은 bamboo과 생성을 요청한 bamboo가 같지 않습니다.");
        }
    }

    protected void 읽은_허가된_bamboo_검증(Page<AllowedBamboo> 읽은_허가된_bamboo, List<AllowedBamboo> 저장할_bamboo) {
        List<Bamboo> 저장한_bamboo_중_허가된_bamboo = 저장할_bamboo.stream()
                .map(AllowedBamboo::getBamboo)
                .toList();

        List<AllowedBamboo> 읽은_허가된_bamboo_배열 = 읽은_허가된_bamboo.get().toList();

        if (저장한_bamboo_중_허가된_bamboo.size() != 읽은_허가된_bamboo_배열.size()) {
            fail("저장한 bamboo의 개수와, 읽은 허가된 bamboo의 개수가 다릅니다.");
        }

        for (AllowedBamboo 허가된_bamboo : 읽은_허가된_bamboo_배열) {
            if (!저장할_bamboo.contains(허가된_bamboo)) {
                fail("저장한 bamboo에 허가된 bamboo이 없습니다.");
            }
        }
    }

    protected AllowedBamboo 최신_bamboo를_조회한다() {
        return bambooBusiness.findMostRecentAllowedBamboo();
    }

    protected void 최신_bamboo임을_검증한다(AllowedBamboo 조회된_최신_bamboo, AllowedBamboo 최신_bamboo) {
        if (!조회된_최신_bamboo.equals(최신_bamboo)) {
            fail("최신 bamboo와 조회된 최신 bamboo이 같지 않습니다.");
        }
    }

    protected List<Bamboo> 허가되지_않은_모든_bamboo_조회하기() {
        return bambooAdminBusiness.findAllBamboo();
    }

    protected void 읽은_허가되지_않은_bamboo_검증(List<Bamboo> 읽은_허가되지_않은_bamboo, List<Bamboo> 저장할_bamboo) {
        if (읽은_허가되지_않은_bamboo.size() != 저장할_bamboo.size()) {
            fail();
        }

        for (Bamboo 허가되지_않은_bamboo : 읽은_허가되지_않은_bamboo) {
            if (!저장할_bamboo.contains(허가되지_않은_bamboo)) {
                fail("저장한 bamboo에 허가되지 않은 bamboo이 없습니다.");
            }
        }
    }

    protected AllowedBamboo bamboo를_허가하기(Bamboo 허가되지_않은_bamboo, User 관리자) {
        return bambooAdminBusiness.allowBamboo(허가되지_않은_bamboo.getId(), 관리자);
    }

    protected void bamboo가_허가되었는지_검증(AllowedBamboo 허가된_bamboo, Bamboo 허가되지_않은_bamboo) {
        if (!허가되지_않은_bamboo.getIsAllow()) {
            fail("bamboo가 허가됨으로 변경되지 않았습니다.");
        }

        AllowedBamboo 조회된_허가된_bamboo = em.createQuery("select a from AllowedBamboo a where a.bamboo = :allowedBamboo", AllowedBamboo.class)
                .setParameter("allowedBamboo", 허가되지_않은_bamboo)
                .getSingleResult();
        if (조회된_허가된_bamboo == null) {
            fail("허가된_bamboo가_생성되지_않았습니다.");
        }

        if (!조회된_허가된_bamboo.equals(허가된_bamboo)) {
            fail("조회된 bamboo가 허가된 bamboo와 같지 않습니다.");
        }
    }

    protected Bamboo bamboo_단건_저장(Bamboo bamboo) {
        em.persist(bamboo);
        return bamboo;
    }

    protected AllowedBamboo 허가된_bamboo_단건_저장(AllowedBamboo allowedBamboo) {
        em.persist(allowedBamboo);
        return allowedBamboo;
    }

    protected User 관리자() {
        return User.builder()
                .id(1L)
                .build();
    }

    protected void bamboo_삭제(Bamboo bamboo) {
        bambooAdminBusiness.deleteBamboo(bamboo.getId());
    }

    protected void bamboo가_삭제되었는지_검증(Bamboo bamboo) {
        Bamboo 아이디로_조회 = em.find(Bamboo.class, bamboo.getId());
        if (아이디로_조회 != null) {
            fail("bamboo가 삭제되지 않았습니다.");
        }
    }

    protected void 허가된_bamboo가_삭제되었는지_검증(AllowedBamboo 허가된_bamboo) {
        if (em.find(AllowedBamboo.class, 허가된_bamboo.getBamboo().getId()) != null) {
            fail("bamboo가 삭제되지 않았습니다.");
        }

        if (em.find(AllowedBamboo.class, 허가된_bamboo.getId()) != null) {
            fail("허가된 bamboo가 삭제되지 않았습니다.");
        }
    }
}
