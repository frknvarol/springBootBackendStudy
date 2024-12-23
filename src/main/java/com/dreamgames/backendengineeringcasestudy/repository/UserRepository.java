package com.dreamgames.backendengineeringcasestudy.repository;
import java.util.List;
import java.util.Optional;

import com.dreamgames.backendengineeringcasestudy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.abGroup = :abGroup ORDER BY RAND() LIMIT 10")
    List<User> findRandomPlayerFromSameGroup(@Param("abGroup") Character ab_group);


}
