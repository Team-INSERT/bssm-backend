package com.insert.ogbsm.service.user;

import com.insert.ogbsm.global.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    public UserResponseDto findMyInfo() {
        return new UserResponseDto(SecurityUtil.getCurrentUserWithLogin());
    }
}
