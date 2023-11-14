package com.insert.ogbsm.integration.bamboo.business;

import com.insert.ogbsm.domain.bamboo.AllowedBamboo;
import com.insert.ogbsm.domain.bamboo.Bamboo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.integration.bamboo.implement.BambooTestImplement;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BambooBusinessTest extends BambooTestImplement {

    @Autowired
    EntityManager entityManager;

    @Nested
    class BambooBusiness에_대한_테스트 {
        @Test
        void Bamboo_저장한다() {
            //given
            Bamboo bamboo_생성_요청 = 허가되지_않은_bamboo();

            //when
            저장하기(bamboo_생성_요청);

            //then
            저장_결과를_검증(bamboo_생성_요청);
        }

        @Test
        void 모든_허가된_글을_읽는다() {
            //data
            int 읽을_페이지 = 0;
            int 읽을_개수 = 5;
            List<AllowedBamboo> 저장할_bamboos = List.of(
                    허가된_bamboo(),
                    허가된_bamboo(),
                    허가된_bamboo(),
                    허가된_bamboo()
            );

            //given
            허가된_bamboo_전체_저장(
                    저장할_bamboos
            );

            //when
            Page<AllowedBamboo> 조회한_허가된_bamboos = 허가된_bamboo_전체_조회하기(읽을_페이지, 읽을_개수);

            //then
            읽은_허가된_bamboo_검증(조회한_허가된_bamboos, 저장할_bamboos);
        }

        @Test
        void 가장_최근에_허가된_bamboo를_읽는다() {
            //data
            AllowedBamboo 최신_bamboo = 허가된_bamboo();
            List<AllowedBamboo> 저장할_bamboos = List.of(
                    허가된_bamboo(),
                    허가된_bamboo(),
                    허가된_bamboo(),
                    최신_bamboo
            );
            //given
            허가된_bamboo_전체_저장(
                    저장할_bamboos
            );

            //when
            AllowedBamboo 조회된_최신_bamboo = 최신_bamboo를_조회한다();

            //then
            최신_bamboo임을_검증한다(조회된_최신_bamboo, 최신_bamboo);
        }
    }

    @Nested
    class BambooAdminBusiness에_대한_테스트 {
        @Test
        void 모든_허가된_되지_않은_bamboo를_읽는다() {
            //data
            List<Bamboo> 저장할_bamboo = List.of(
                    허가되지_않은_bamboo(),
                    허가되지_않은_bamboo(),
                    허가되지_않은_bamboo(),
                    허가되지_않은_bamboo()
            );

            //given
            허가되지_않은_bamboo_전체_저장(
                    저장할_bamboo
            );

            //when
            List<Bamboo> 조회한_허가되지_않은_bamboo = 허가되지_않은_모든_bamboo_조회하기();

            //then
            읽은_허가되지_않은_bamboo_검증(조회한_허가되지_않은_bamboo, 저장할_bamboo);
        }

        @Nested
        class bamboo를_허가한다 {
            @Test
            void 정확한_bamboo가_들어왔을_때_bamboo를_허가한다() {
                //given
                Bamboo 허가되지_않은_bamboo = bamboo_단건_저장(허가되지_않은_bamboo());
                User 관리자 = 관리자();

                //when
                AllowedBamboo 허가된_bamboo = bamboo를_허가하기(허가되지_않은_bamboo, 관리자);

                //then
                bamboo가_허가되었는지_검증(허가된_bamboo, 허가되지_않은_bamboo);
            }

            @Test
            void 저장되어있지_않은_bamboo가_들어왔을_때_BAMBOO_NOT_FOUND가_발생한다() {
                //given
                Bamboo 삭제된_bamboo = bamboo_단건_저장(허가되지_않은_bamboo());
                bamboo_삭제(삭제된_bamboo);
                User 관리자 = 관리자();

                //when, then
                exceptionHandler.함수를_실행할_시_다음의_에러코드를_반환한다(
                        () -> bamboo를_허가하기(삭제된_bamboo, 관리자),
                        ErrorCode.BAMBOO_NOT_FOUND);
            }

            @Test
            void 이미_허가된_bamboo를_허가하면_BAMBOO_ALREADY_ALLOWED가_발생한다() {
                //given
                Bamboo 허가되지_않은_bamboo = bamboo_단건_저장(허가되지_않은_bamboo());
                User 관리자 = 관리자();

                bamboo를_허가하기(허가되지_않은_bamboo, 관리자);
                //when, then
                exceptionHandler.함수를_실행할_시_다음의_에러코드를_반환한다(
                        () -> bamboo를_허가하기(허가되지_않은_bamboo, 관리자),
                        ErrorCode.BAMBOO_ALREADY_ALLOWED);

            }
        }

        @Nested
        @Transactional
        class bamboo를_삭제한다 {
            @Test
            void bamboo이_허가가_되지_않았다면_bamboo만_삭제한다() {
                //given
                Bamboo bamboo = bamboo_단건_저장(허가되지_않은_bamboo());

                //when
                bamboo_삭제(bamboo);

                //then
                bamboo가_삭제되었는지_검증(bamboo);
            }

            @Test
            void bamboo가_허가가_되었다면_허가된_bamboo까지_함께_삭제한다() {
                //given
                AllowedBamboo 허가된_bamboo = 허가된_bamboo_단건_저장(허가된_bamboo());

                //when
                bamboo_삭제(허가된_bamboo.getBamboo());

                //then
                허가된_bamboo가_삭제되었는지_검증(허가된_bamboo);
            }

            @Test
            void bamboo가_allowed이지만_허가된_bamboo가_없다면_BAMBOO_NOT_FOUND가_발생한다() {
                //given
                Bamboo bamboo가_allowed이지만_허가된_bamboo가_없는_bamboo = bamboo_단건_저장(bamboo는_allowed이지만_허가된_bamboo가_없는_bamboo());
                //when, then
                exceptionHandler.함수를_실행할_시_다음의_에러코드를_반환한다(
                        () -> bamboo_삭제(bamboo가_allowed이지만_허가된_bamboo가_없는_bamboo),
                        ErrorCode.BAMBOO_NOT_FOUND
                );
            }
        }
    }
}
