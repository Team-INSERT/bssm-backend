package com.insert.ogbsm.domain.like.repo;

import com.insert.ogbsm.domain.like.Likes;
import com.insert.ogbsm.domain.like.type.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikesRepo extends JpaRepository<Likes, Long> {
    @Query("select l " +
            "from Likes l " +
            "where l.userId = :userId and " +
            "l.type = :type and " +
            "l.partyId = :partyId")
    Optional<Likes> findByUserIdAndTypeAndPartyId(Long userId, Type type, Long partyId);

    Optional<Likes> findByUserIdAndPartyIdAndType(Long userId, Long partyId, Type type);
}
