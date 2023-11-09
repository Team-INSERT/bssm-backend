package com.insert.ogbsm.presentation.checkIn;

import com.insert.ogbsm.domain.room.type.DormitoryType;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.checkIn.dto.CheckInRes;
import com.insert.ogbsm.service.checkIn.business.CheckInBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkIn")
public class CheckInController {
    private final CheckInBusiness checkInBusiness;

    @PostMapping
    public Long checkIn() {
        return checkInBusiness.checkIn(SecurityUtil.getCurrentUserIdWithoutLogin());
    }

//    @GetMapping
//    public boolean getMyCheckIn() {
//        return checkInRead.getMyCheckIn(SecurityUtil.getCurrentUserIdWithoutLogin());
//    }

    @GetMapping
    public CheckInRes getMyCheckInWithRoom() { return checkInBusiness.getMyRoom(SecurityUtil.getCurrentUserIdWithoutLogin()); }

    @GetMapping("/all")
    public List<CheckInRes> getAllCheckIn(@RequestParam(name = "type", defaultValue = "all") String dormitoryType) {
        return checkInBusiness.getCheckIn(dormitoryType);
    }
}
