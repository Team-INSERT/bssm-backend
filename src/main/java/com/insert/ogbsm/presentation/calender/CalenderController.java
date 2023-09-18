package com.insert.ogbsm.presentation.calender;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.calender.dto.*;
import com.insert.ogbsm.service.calender.CalenderDefService;
import com.insert.ogbsm.service.calender.CalenderReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;

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

    @QueryMapping
    public CalenderGraphRes getCalender(@Argument(name = "input") CalenderReadReq calenderReadReq) {
        return calenderReadService.get(calenderReadReq);
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
