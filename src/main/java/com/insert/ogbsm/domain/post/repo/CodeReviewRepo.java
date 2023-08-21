package com.insert.ogbsm.domain.post.repo;

import com.insert.ogbsm.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeReviewRepo extends JpaRepository<Post, Long> {
}
