package com.insert.ogbsm.service.post.business;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.presentation.post.dto.PostLikeRes;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.post.implement.PostImplement;
import com.insert.ogbsm.service.validation.PostValidation;
import com.insert.ogbsm.service.post.implement.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostBusiness {

    public final PostImplement postImplement;
    private final UserValidation userValidation;
    private final PostValidation postValidation;

    public PostRes create(Post post, User user) {
        postImplement.append(post);
        return new PostRes(post, user);
    }

    public PostRes update(Post post, User user) {
        Post updatablePost = postImplement.read(post.getId());

        userValidation.mustBeSameUser(updatablePost.getWriterId(), user.getId());
        updatablePost.update(post);

        return new PostRes(updatablePost, user);
    }

    public void delete(Long id, Long userId) {
        Post post = postImplement.read(id);
        userValidation.mustBeSameUser(post.getWriterId(), userId);
        postImplement.delete(post);
    }

    public PostRes updateLostAndFound(Long postId, Long foundUserId, User user) {
        Post post = postImplement.read(postId);

        userValidation.mustBeSameUser(post.getWriterId(), user.getId());
        postValidation.mustBeLostOrFound(post);

        post.getLostFound().updateFoundUserId(foundUserId);

        return new PostRes(post, user, postImplement.readUser(foundUserId));
    }

    public PostLikeRes readOne(Long id, Long userId) {
        Post post = postImplement.read(id);
        User user = postImplement.readUser(post.getWriterId());
        boolean doesLike = postImplement.doesLikePost(userId, post);

        return new PostLikeRes(post, user, doesLike);
    }

    public Pagination<List<PostRes>> readByCategory(Category category, Pageable pageable) {
        Page<Post> pagePost = postImplement.readByCategory(category, pageable);
        return postImplement.fillUser(pageable, pagePost);
    }

    public List<PostRes> readTop5ByCategory(Category category) {
        return postImplement.readTop(5, category);
    }
}
