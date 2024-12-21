package com.dreamgames.backendengineeringcasestudy.repository;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
    Optional<Partnership> findByUser1IdOrUser2Id(Long userId1, Long userId2);
    Optional<Partnership> findByUser1IdAndUser2Id(Long userId1, Long userId2);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Partnership p " +
            "WHERE (p.user1.id = :userId OR p.user2.id = :userId) " +
            "AND p.event.id = :eventId")
    boolean existsByUserIdAndEventId(@Param("userId") Long userId, @Param("eventId") Long eventId);
}
