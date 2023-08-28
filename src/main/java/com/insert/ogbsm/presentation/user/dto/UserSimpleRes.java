package com.insert.ogbsm.presentation.user.dto;

import com.insert.ogbsm.domain.user.User;
import lombok.Getter;

@Getter
public class UserSimpleRes {
    final Long id;
    final String nickName;
    final String profileImage;

    public UserSimpleRes(User user) {
        this.id = user.getId();
        this.nickName = user.getNickname();
        this.profileImage = user.getProfile_image();
    }
}
