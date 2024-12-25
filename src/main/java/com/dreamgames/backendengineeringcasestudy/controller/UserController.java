package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.dto.request.GetSuggestionsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.SuggestionsResponse;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(String username) {
        User newUser = userService.createUser(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{userId}/progress")
    public ResponseEntity<User> updateUserProgress(@PathVariable Long userId) {
        User updatedUser = userService.updateUserProgress(userId);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/suggestions")
    public ResponseEntity<SuggestionsResponse> getSuggestions(@RequestBody GetSuggestionsRequest request) {
        SuggestionsResponse response = userService.getSuggestions(request);
        return ResponseEntity.ok(response);
    }
}
