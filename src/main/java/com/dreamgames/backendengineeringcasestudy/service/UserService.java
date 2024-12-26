package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.dto.request.GetSuggestionsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.UpdateUserProgressRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetSuggestionsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.UpdateUserProgressResponse;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.model.User;
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

    @Autowired
    public UserService(UserRepository userRepository, PartnershipRepository partnershipRepository, EventService eventService) {
        this.userRepository = userRepository;
        this.eventService = eventService;
        this.partnershipRepository = partnershipRepository;

    }


    @Transactional
    public User createUser(String username) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setLevel(1);
        newUser.setCoins(2000);

        Character group = Math.random() < 0.5 ? 'A' : 'B';
        newUser.setAbGroup(group);

        return userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
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
                partnership.setHeliumCount(partnership.getHeliumCount() + 10);
                partnershipRepository.save(partnership);

                partnership.setBalloonProgress(partnership.getBalloonProgress() + 10);

                partnership.setHeliumCount(partnership.getHeliumCount() + 10);
                partnershipRepository.save(partnership);
            }
        }

        userRepository.save(user);

        return new UpdateUserProgressResponse(user.getId(), user.getLevel(), user.getCoins());
    }

    private Character getABGroup(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return user.getAbGroup();
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


    private boolean isUserEligibleForHelium(User user) {
        return user.getLevel() >= 50;
    }
}

