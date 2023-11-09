package com.insert.ogbsm.presentation.ber;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.ber.dto.BerReadRes;
import com.insert.ogbsm.presentation.ber.dto.BerReserveReq;
import com.insert.ogbsm.service.ber.business.BerBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ber")
public class BerController {

    private final BerBusiness berBusiness;

    @PostMapping
    public Long BerReserve(@RequestBody BerReserveReq berReserveReq) {
        return berBusiness.berReserve(berReserveReq.to(SecurityUtil.getCurrentUserIdWithLogin()));
    }

    @GetMapping
    public BerReadRes findBer(@RequestParam(name = "date") LocalDate localDate) {
        if (localDate == null) localDate = LocalDate.now();
        return berBusiness.getBer(localDate);
    }

    @DeleteMapping({"/{id}"})
    public void deleteBer(@PathVariable(name = "id") Long id) {
        berBusiness.berCancel(id, SecurityUtil.getCurrentUserIdWithLogin());
    }


}
