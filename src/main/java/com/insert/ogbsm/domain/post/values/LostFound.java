package com.insert.ogbsm.domain.post.values;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LostFound {

    @Column(columnDefinition = "TEXT")
    private String lostThingImage;

    //TODO PERSON WHO FOUND


    public LostFound(String lostThingImage) {
        this.lostThingImage = lostThingImage;
    }

    public void update(String lostTingImage) {
        this.lostThingImage = lostTingImage;
    }
}
