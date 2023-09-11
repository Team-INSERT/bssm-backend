package com.insert.ogbsm.presentation.like;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.global.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.like.dto.LikesReq;
import com.insert.ogbsm.service.like.LikeDefService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {
    private final LikeDefService likeDefService;

    @PutMapping("/update")
    boolean updateLikes(@RequestBody LikesReq likesReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        return likeDefService.changeLikeStatus(likesReq, user.getId());
    }


}
