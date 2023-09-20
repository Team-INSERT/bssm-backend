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

    private Long foundUserId;

    private String place;

    private String keepingPlace;

    public void updateFoundUserId(Long foundUserId) {
        this.foundUserId = foundUserId;
    }

    public LostFound(String lostThingImage, String place, String keepingPlace) {
        this.lostThingImage = lostThingImage;
        this.place = place;
        this.keepingPlace = keepingPlace;
    }
}
