package com.dreamgames.backendengineeringcasestudy.repository;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface PartnershipRepository extends CrudRepository<Partnership, Long> {
    Optional<Partnership> findByUser1IdOrUser2Id(Long userId1, Long userId2);
}
