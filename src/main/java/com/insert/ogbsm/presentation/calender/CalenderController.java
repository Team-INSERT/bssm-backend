package com.insert.ogbsm.presentation.calender;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadReq;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderReq;
import com.insert.ogbsm.presentation.calender.dto.CalenderRes;
import com.insert.ogbsm.presentation.calender.dto.PriorityUpdateReq;
import com.insert.ogbsm.service.calender.CalenderDefService;
import com.insert.ogbsm.service.calender.CalenderReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender")
public class CalenderController {
    private final CalenderReadService calenderReadService;
    private final CalenderDefService calenderDefService;

    @PostMapping
    public CalenderRes create(@RequestBody CalenderReq calenderReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();
        return calenderDefService.create(calenderReq, user);
    }

    @GetMapping
    public List<CalenderReadRes> getCalender(CalenderReadReq calenderReadReq) {
        return calenderReadService.get(calenderReadReq, SecurityUtil.getOptUser());
    }

    @PutMapping("/priority")
    public void updatePriority(@RequestBody PriorityUpdateReq priorityUpdateReq) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        calenderDefService.updatePriority(priorityUpdateReq.calenderId(), priorityUpdateReq.priority(), user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        User user = SecurityUtil.getCurrentUserWithLogin();

        calenderDefService.delete(id, user);
    }
}
