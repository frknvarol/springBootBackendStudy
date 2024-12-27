package com.dreamgames.backendengineeringcasestudy.repository;

import com.dreamgames.backendengineeringcasestudy.model.Leaderboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LeaderboardRepository extends MongoRepository<Leaderboard, Long> {

    @Query("SELECT l FROM Leaderboard l ORDER BY l.level DESC LIMIT 100")  // Adjust if you want to use score instead of level
    List<Leaderboard> findTop100ByOrderByLevelDesc();

}
