package com.insert.ogbsm.domain.post.repo;

import com.insert.ogbsm.domain.post.Post;
import com.insert.ogbsm.domain.post.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.category = :category order by p.createdAt desc")
    List<Post> findByCategory(Category category);
}
