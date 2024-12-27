package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.dto.dto.LeaderboardDTO;
import com.dreamgames.backendengineeringcasestudy.model.Leaderboard;
import com.dreamgames.backendengineeringcasestudy.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    @Autowired
    public LeaderboardService(LeaderboardRepository leaderboardRepository) {
        this.leaderboardRepository = leaderboardRepository;
    }

    public List<LeaderboardDTO> getTop100Users() {
        List<Leaderboard> topUsers = leaderboardRepository.findTop100ByOrderByLevelDesc();

        return topUsers.stream()
                .map(user -> new LeaderboardDTO(user.getUser().getId(), user.getLevel(), user.getUsername()))
                .collect(Collectors.toList());
    }


}
