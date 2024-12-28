package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.dto.request.CreateUserRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetSuggestionsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.UpdateUserProgressRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.CreateUserResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetSuggestionsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.UpdateUserProgressResponse;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Leaderboard;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.LeaderboardRepository;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PartnershipRepository partnershipRepository;
    private final EventService eventService;
    private final LeaderboardRepository leaderboardRepository;

    @Autowired
    public UserService(UserRepository userRepository, PartnershipRepository partnershipRepository, EventService eventService, LeaderboardRepository leaderboardRepository) {
        this.userRepository = userRepository;
        this.eventService = eventService;
        this.partnershipRepository = partnershipRepository;
        this.leaderboardRepository = leaderboardRepository;

    }


    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setLevel(1);
        newUser.setCoins(2000);

        Character group = Math.random() < 0.5 ? 'A' : 'B';
        newUser.setAbGroup(group);

        userRepository.save(newUser);

        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setLevel(newUser.getLevel());
        leaderboard.setUser(newUser);
        leaderboard.setUsername(newUser.getUsername());
        leaderboardRepository.save(leaderboard);

        return new CreateUserResponse(newUser.getId(), 1, 2000, newUser.getAbGroup());
    }


    public UpdateUserProgressResponse updateUserProgress(UpdateUserProgressRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        user.setLevel(user.getLevel() + 1);
        user.setCoins(user.getCoins() + 100);

        Optional<Event> activeEventOpt = eventService.getActiveEvent();

        if (activeEventOpt.isPresent()) {
            Long currentEventId = activeEventOpt.get().getId();

            Partnership partnership = partnershipRepository.findByUserIdAndEventId(user.getId(), currentEventId);
            if (partnership != null) {
                partnership.setHeliumCount(partnership.getHeliumCount() + 50);
                partnershipRepository.save(partnership);

            }
        }

        userRepository.save(user);

        Leaderboard leaderboard = leaderboardRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Leaderboard entry not found"));
        leaderboard.setLevel(user.getLevel());
        leaderboardRepository.save(leaderboard);

        return new UpdateUserProgressResponse(user.getId(), user.getLevel(), user.getCoins());
    }


    public GetSuggestionsResponse getSuggestions(GetSuggestionsRequest request) {
        List<User> suggestedUsers = userRepository.findRandomPlayerFromSameGroup(request.getAbGroup());

        return new GetSuggestionsResponse(suggestedUsers);
    }

    public boolean hasPartner(Long userId) {
        Long activeEventId = eventService.getActiveEvent()
                .orElseThrow(() -> new RuntimeException("No active event"))
                .getId();

        return partnershipRepository.existsByUserIdAndEventId(userId, activeEventId);
    }


}

