package com.insert.ogbsm.domain.post.repo;

import com.insert.ogbsm.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    @Query("select p from Post p order by p.createdDate desc")
    List<Post> findAllCreateTimeDesc();
}
