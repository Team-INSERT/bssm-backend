package com.insert.ogbsm.service.room;

import com.insert.ogbsm.domain.room.repo.RoomRepo;
import com.insert.ogbsm.presentation.room.dto.RoomRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomRead {
    private final RoomRepo roomRepo;

    public List<RoomRes> getRoom() {
        return roomRepo.findAllByYearSemester();
    }


}
