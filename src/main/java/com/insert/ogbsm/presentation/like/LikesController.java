package com.insert.ogbsm.presentation.like;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.like.dto.LikesReq;
import com.insert.ogbsm.service.like.business.LikeDefBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {
    private final LikeDefBusiness likeDefBusiness;

    @PutMapping("/update")
    boolean updateLikes(@RequestBody LikesReq likesReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return likeDefBusiness.changeLikeStatus(likesReq.toEntity(user.getId()));
    }


}
