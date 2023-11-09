package com.insert.ogbsm.presentation.calender;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.calender.dto.*;
import com.insert.ogbsm.service.calender.business.CalenderBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender")
public class CalenderController {
    private final CalenderBusiness calenderBusiness;

    @PostMapping
    public CalenderRes create(@RequestBody CalenderReq calenderReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();
        return calenderBusiness.append(calenderReq.toEntity(user.getId()), user);
    }

    @GetMapping
    public List<CalenderReadRes> getCalender(CalenderReadReq calenderReadReq) {
        return calenderBusiness.read(calenderReadReq.year(), calenderReadReq.month(), SecurityUtil.getOptUser());
    }

    @PutMapping("/priority")
    public void updatePriority(@RequestBody PriorityUpdateReq priorityUpdateReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        calenderBusiness.updatePriority(priorityUpdateReq.calenderId(), priorityUpdateReq.priority(), user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        calenderBusiness.remove(id, user);
    }
}
