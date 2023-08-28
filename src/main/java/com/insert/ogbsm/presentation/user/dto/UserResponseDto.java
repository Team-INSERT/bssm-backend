package com.insert.ogbsm.presentation.user.dto;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.authority.Authority;
import com.insert.ogbsm.domain.user.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private Long id;
    private String nickname;
    private String email;
    private String name;
    private String profile_url;
    private String profile_image;
    private Authority authority;
    private Role role;
    private Long enroll;
    private Short grade;
    private Short classNum;
    private Short studentNumber;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.name = user.getName();
        this.profile_image = user.getProfile_image();
        this.profile_url = "https://auth.bssm.kro.kr/user" + user.getId();
        this.authority = user.getAuthority();
        this.role = user.getRole();
        this.enroll = user.getEnroll();
        this.grade = user.getGrade();
        this.classNum = user.getClass_number();
        this.studentNumber = user.getStudent_number();
    }

}
