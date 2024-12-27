package com.dreamgames.backendengineeringcasestudy.repository;

import com.dreamgames.backendengineeringcasestudy.model.Leaderboard;
import com.dreamgames.backendengineeringcasestudy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    @Query("SELECT l FROM Leaderboard l ORDER BY l.level DESC LIMIT 100")
    List<Leaderboard> findTop100ByOrderByLevelDesc();

    @Query("SELECT l FROM Leaderboard l WHERE l.user = :user ")
    Optional<Leaderboard> findByUser(@Param("user") User user);

}
