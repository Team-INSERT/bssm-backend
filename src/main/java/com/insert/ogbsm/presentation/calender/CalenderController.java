package com.insert.ogbsm.presentation.calender;

import com.insert.ogbsm.domain.calender.repo.CalenderRepo;
import com.insert.ogbsm.presentation.calender.dto.CalenderGraphRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadReq;
import com.insert.ogbsm.service.calender.CalenderDefService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender")
public class CalenderController {
    private final CalenderRepo calenderRepo;
    private final CalenderDefService calenderDefService;

    @QueryMapping
    public CalenderGraphRes getCalender(@Argument(name = "input") CalenderReadReq calenderReadReq) {
        return calenderRepo.get(calenderReadReq.month(), calenderReadReq.grade(), calenderReadReq.classNumber());
    }

}
