package com.insert.ogbsm.presentation.calender.dto;

import com.insert.ogbsm.domain.calender.Calender;

import java.util.List;

public record CalenderSimpleRes(
        List<Calender> calenders
) {
}
