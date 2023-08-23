package com.insert.ogbsm.domain.post.repo;

import com.insert.ogbsm.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
}
