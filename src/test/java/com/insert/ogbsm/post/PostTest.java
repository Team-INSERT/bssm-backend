package com.insert.ogbsm.post;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.repo.PostRepo;
import com.insert.ogbsm.service.post.PostDefService;
import com.insert.ogbsm.service.post.PostReadService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class PostTest {

    @Autowired
    private PostRepo postRepo;
    private PostDefService postDefService;
    private PostReadService postReadService;
    private Post post = new Post("title", "content");

    @BeforeEach
    public void init() {
        postDefService = new PostDefService(postRepo);
        postReadService = new PostReadService(postRepo);
    }

    @Test
    @Order(1)
    public void create() {
        post = postDefService.create(post);
    }

    @Test
    @Order(2)
    public void read() {
        post = postDefService.create(post);

        List<Post> list = postReadService.getPostDayDesc();

        assertThat(list.equals(Collections.singletonList(post))).isTrue();
    }

    @Test
    @Order(3)
    public void update() {
        post = postDefService.create(post);
        Post foundPost = postReadService.getPostById(post.getId());
        foundPost.update("new title", "new content");
        Post newFoundPost = postReadService.getPostById(post.getId());

        assertAll(
                () -> assertThat(post.getTitle()).isEqualTo(newFoundPost.getTitle()),
                () -> assertThat(post.getContent()).isEqualTo(newFoundPost.getContent())
        );
    }

    @Test
    @Order(4)
    public void delete() {
        post = postDefService.create(post);
        postDefService.delete(post.getId());

        List<Post> list = postReadService.getPostDayDesc();

        assertThat(list).isEmpty();
    }
}
