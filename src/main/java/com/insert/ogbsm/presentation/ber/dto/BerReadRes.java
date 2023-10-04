package com.insert.ogbsm.presentation.ber.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BerReadRes {
    private final List<Integer> reservedBerNumber;
    private final List<BerRes> berResList;

    public BerReadRes(List<BerRes> berResList) {
        this.berResList = berResList;
        this.reservedBerNumber = berResList.stream()
                .map(BerRes::getBerNumber)
                .toList();
    }
}
