package com.insert.ogbsm.presentation.room;

import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.room.dto.AllocateRoomReq;
import com.insert.ogbsm.presentation.room.dto.RoomRes;
import com.insert.ogbsm.service.room.RoomDef;
import com.insert.ogbsm.service.room.RoomRead;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    private final RoomRead roomRead;
    private final RoomDef roomDef;

    @PostMapping("/allow")
    public boolean allowRoom() {
        return roomDef.setAllocateRoomPublic();
    }

    @PostMapping("/allocate")
    public Long allocateRoom(@RequestBody AllocateRoomReq allocateRoomReq) {
        return roomDef.allocateRoom(allocateRoomReq, SecurityUtil.getCurrentUserIdWithLogin());
    }

    @GetMapping
    public List<RoomRes> getRoom() {
        return roomRead.getRoom();
    }
}
