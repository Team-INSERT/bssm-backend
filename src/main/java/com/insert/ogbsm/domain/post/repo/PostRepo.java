package com.insert.ogbsm.domain.post.repo;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {

    Page<Post> findByCategoryOrderByLikeCountDescCreatedAtDesc(Category category, Pageable pageable);

    Page<Post> findByCategoryOrderByCreatedAtDesc(Category category, Pageable pageable);
}
