package com.dreamgames.backendengineeringcasestudy.repository;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
    Optional<Partnership> findByUser1IdOrUser2Id(Long userId1, Long userId2);
}
