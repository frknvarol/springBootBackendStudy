package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

    // Method to create a new user
    public User createUser() {
        // Generate a unique user ID (if necessary) - depending on your DB auto-generation settings
        User newUser = new User();
        newUser.setLevel(1); // Default starting level
        newUser.setCoins(2000); // Starting coins

        // Assign user to A/B Test group randomly (or based on a rule)
        Character group = Math.random() < 0.5 ? 'A' : 'B'; // Random assignment
        newUser.setAbGroup(group);

        // Save the new user to the database
        return userRepository.save(newUser); // Assuming you're using JPA repository
    }

    // Method to update user's progress
    public User updateUserProgress(Long userId) {
        // Fetch the user from the database
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Update the user's level and coin count
        user.setLevel(user.getLevel() + 1);
        user.setCoins(user.getCoins() + 100);

        // Check if the "Pop the Balloon" event is active
        if (isPopTheBalloonEventActive()) {
            // Check if the user is in a partnership
            Optional<Partnership> partnershipOpt = partnershipRepository.findByUser1IdOrUser2Id(userId, userId);

            if (partnershipOpt.isPresent()) {
                Partnership partnership = partnershipOpt.get();

                // Increase the helium count if the user is eligible (e.g., level >= 5)
                if (isUserEligibleForHelium(user)) {
                    partnership.setHeliumCount(partnership.getHeliumCount() + 1); // Increase helium count for the partnership
                    partnershipRepository.save(partnership); // Save the updated partnership
                }
            }
        }

        // Save the updated user data
        return userRepository.save(user);
    }

    // Helper method to check if the "Pop the Balloon" event is active
    private boolean isPopTheBalloonEventActive() {
        // Implement the logic to check if the event is active
        // This could involve checking a flag in the database, an external service, etc.
        return true; // Assuming it's always active for simplicity
    }

    // Helper method to check if the user is eligible for helium (e.g., depending on their level or event rules)
    private boolean isUserEligibleForHelium(User user) {
        // Example eligibility check: user level >= 5
        return user.getLevel() >= 5;
    }
}

