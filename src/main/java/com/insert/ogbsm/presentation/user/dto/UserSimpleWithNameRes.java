package com.insert.ogbsm.presentation.user.dto;

import com.insert.ogbsm.domain.user.User;
import lombok.Getter;

@Getter
public class UserSimpleWithNameRes {
    final Long id;
    final String nickName;
    final String name;
    final String profileImage;

    public UserSimpleWithNameRes(User user) {
        this.id = user.getId();
        this.nickName = user.getNickname();
        this.profileImage = user.getProfile_image();
        this.name = user.getName();

    }
}
