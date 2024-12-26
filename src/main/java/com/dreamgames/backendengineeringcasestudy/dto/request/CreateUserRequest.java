package com.dreamgames.backendengineeringcasestudy.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank(message = "username cannot be blank")
    private String username;

    public CreateUserRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
