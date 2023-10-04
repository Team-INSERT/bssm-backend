package com.insert.ogbsm.presentation.ber;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.ber.dto.BerReadRes;
import com.insert.ogbsm.presentation.ber.dto.BerReserveReq;
import com.insert.ogbsm.service.ber.BerDef;
import com.insert.ogbsm.service.ber.BerRead;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ber")
public class BerController {

    private final BerDef berDef;
    private final BerRead berRead;

    @PostMapping
    public Long BerReserve(@RequestBody BerReserveReq berReserveReq) {
        return berDef.berReserve(berReserveReq, SecurityUtil.getCurrentUserIdWithLogin());
    }

    @GetMapping
    public BerReadRes findBer(@RequestParam(name = "date") LocalDate localDate) {
        if (localDate == null) localDate = LocalDate.now();
        return berRead.getBer(localDate);
    }

    @DeleteMapping({"/{id}"})
    public void deleteBer(@PathVariable(name = "id") Long id) {
        berDef.berCancel(id, SecurityUtil.getCurrentUserIdWithLogin());
    }


}
