package com.insert.ogbsm.presentation.post.dto;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.user.User;
import lombok.Getter;

@Getter
public class PostLikeRes extends PostRes {

    final boolean doesLike;

    public PostLikeRes(Post post, User user, boolean doesLike) {
        super(post, user);
        this.doesLike = doesLike;
    }
}
