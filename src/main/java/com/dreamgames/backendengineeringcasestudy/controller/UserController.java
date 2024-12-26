package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.dto.request.CreateUserRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetSuggestionsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.UpdateUserProgressRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.CreateUserResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetSuggestionsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.UpdateUserProgressResponse;
import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.service.UserService;
import jakarta.validation.Valid;
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

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User createdUser = userService.createUser(request.getUsername());

        CreateUserResponse response = new CreateUserResponse(
                createdUser.getId(),
                createdUser.getLevel(),
                createdUser.getCoins(),
                createdUser.getAbGroup()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{userId}/progress")
    public ResponseEntity<UpdateUserProgressResponse> updateUserProgress(@RequestBody UpdateUserProgressRequest request) {
        UpdateUserProgressResponse response = userService.updateUserProgress(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/suggestions")
    public ResponseEntity<GetSuggestionsResponse> getSuggestions(@RequestBody GetSuggestionsRequest request) {
        GetSuggestionsResponse response = userService.getSuggestions(request);
        return ResponseEntity.ok(response);
    }
}
