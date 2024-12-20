package com.dreamgames.backendengineeringcasestudy.service;

import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PartnershipRepository partnershipRepository;

    // Method to create a new user
    public User createUser(String username) {
        // Generate a unique user ID (if necessary) - depending on your DB auto-generation settings
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setLevel(1); // Default starting level
        newUser.setCoins(2000); // Starting coins

        // Assign user to A/B Test group randomly (or based on a rule)
        Character group = Math.random() < 0.5 ? 'A' : 'B'; // Random assignment
        newUser.setAbGroup(group);

        // Save the new user to the database
        return userRepository.save(newUser); // Assuming you're using JPA repository
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getBookById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // Method to update user's progress
    public User updateUserProgress(Long userId) {
        // Fetch the user from the database
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Update the user's level and coin count
        user.setLevel(user.getLevel() + 1);
        user.setCoins(user.getCoins() + 100);

        // Check if the "Pop the Balloon" event is active
        if (isEventActive()) {
            // Check if the user is in a partnership
            Optional<Partnership> partnershipOpt = partnershipRepository.findByUser1IdOrUser2Id(userId, userId);

            if (partnershipOpt.isPresent()) {
                Partnership partnership = partnershipOpt.get();

                // Increase the helium count if the user is eligible (e.g., level >= 50)
                if (isUserEligibleForHelium(user)) {
                    partnership.setHeliumCount(partnership.getHeliumCount() + 10); // Increase helium count for the partnership
                    partnershipRepository.save(partnership); // Save the updated partnership
                }
            }
        }

        // Save the updated user data
        return userRepository.save(user);
    }

    private Character getABGroup(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return user.getAbGroup();
    }

    // Helper method to check if the "Pop the Balloon" event is active
    private boolean isEventActive() {

        // current time in UTC
        ZonedDateTime utcTime = ZonedDateTime.now(ZoneId.of("UTC"));
        LocalTime currentTime = utcTime.toLocalTime();

        return currentTime.isAfter(LocalTime.of(8,0)) && currentTime.isAfter(LocalTime.of(22, 0));
    }

    private boolean isUserEligibleForHelium(User user) {
        return user.getLevel() >= 50;
    }
}

