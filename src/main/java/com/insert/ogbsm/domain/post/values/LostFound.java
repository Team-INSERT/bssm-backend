package com.insert.ogbsm.domain.post.values;

import jakarta.persistence.Column;

public class LostFound {

    @Column(columnDefinition = "TEXT")
    private String lostTingImage;

    //TODO PERSON WHO FOUND


    public LostFound(String lostTingImage) {
        this.lostTingImage = lostTingImage;
    }

    public void update(String lostTingImage) {
        this.lostTingImage = lostTingImage;
    }
}
