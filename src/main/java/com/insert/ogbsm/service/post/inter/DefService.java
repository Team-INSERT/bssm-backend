package com.insert.ogbsm.service.post.inter;

import com.insert.ogbsm.domain.post.PostBase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class DefService<T, REPO> {

    private final JpaRepository<T, Long> jpaRepo;

    protected DefService(REPO repo) {
        this.jpaRepo = (JpaRepository<T, Long>) repo;
    }

    public T create(T entity) {
        return jpaRepo.save(entity);
    }

    public T update(T getEntity, Long entityId) {
        PostBase entity = (PostBase) getEntity;
        T updatablePost = jpaRepo.findById(entityId)
                .orElseThrow((() -> new EntityNotFoundException("Updatable Post Not Found"))); // ToDo

        ((PostBase) updatablePost).update(entity.getTitle(), entity.getContent());

        return updatablePost;
    }

    public void delete(Long postId) {
        // ToDo 사용자가 작성자인지 검증

        jpaRepo.deleteById(postId);
    }
}
