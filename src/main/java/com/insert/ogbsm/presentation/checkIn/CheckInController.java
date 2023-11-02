package com.insert.ogbsm.presentation.checkIn;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.checkIn.dto.CheckInRes;
import com.insert.ogbsm.service.checkIn.CheckInDef;
import com.insert.ogbsm.service.checkIn.CheckInRead;
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
    private final CheckInRead checkInRead;
    private final CheckInDef checkInDef;

    @PostMapping
    public Long checkIn() {
        return checkInDef.checkIn(SecurityUtil.getCurrentUserIdWithoutLogin());
    }

    @GetMapping
    public boolean getMyCheckIn() {
        return checkInRead.getMyCheckIn(SecurityUtil.getCurrentUserIdWithoutLogin());
    }

    @GetMapping("/all")
    public List<CheckInRes> getAllCheckIn(@RequestParam(name = "type", defaultValue = "all") String dormitoryType) {
        return checkInRead.getCheckIn(dormitoryType);
    }
}
