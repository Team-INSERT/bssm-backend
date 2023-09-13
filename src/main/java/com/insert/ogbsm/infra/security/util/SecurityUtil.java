package com.insert.ogbsm.infra.security.util;


import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.infra.security.auth.AuthDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static User getCurrentUserWithLogin() {
        try {
            return getUser();
        } catch (ClassCastException e) {
            throw new BsmException(ErrorCode.USER_NOT_LOGIN);
        }
    }

    public static Long getCurrentUserIdWithLogin() {
        return getCurrentUserWithLogin().getId();
    }

    public static Long getCurrentUserIdWithoutLogin() {
        try {
            return getCurrentUserOrNotLogin().getId();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static User getCurrentUserOrNotLogin() {
        try {
            return getUser();
        } catch (Exception e) {
            return null;
        }
    }

    private static User getUser() {

        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof String) {
            throw new BsmException(ErrorCode.USER_NOT_LOGIN);
        }

        AuthDetails authDetails = (AuthDetails) principal;

        return authDetails.getUser();
    }
}
