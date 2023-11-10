package com.insert.ogbsm.presentation.room;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.room.dto.AllocateRoomReq;
import com.insert.ogbsm.presentation.room.dto.RoomRes;
import com.insert.ogbsm.service.room.business.RoomBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    private final RoomBusiness roomBusiness;

    @PostMapping("/allow")
    public boolean allowRoom() {
        return roomBusiness.setAllocateRoomPublic();
    }

    @PostMapping("/allocate")
    public void allocateRoom(@RequestBody AllocateRoomReq allocateRoomReq) {
        roomBusiness.allocateRoom(allocateRoomReq, SecurityUtil.getCurrentUserIdWithLogin());
    }
}
