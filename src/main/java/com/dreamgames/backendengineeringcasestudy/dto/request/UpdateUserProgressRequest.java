package com.dreamgames.backendengineeringcasestudy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserProgressRequest {
    @JsonProperty("userId")
    private Long userId;

    public UpdateUserProgressRequest() {}

    public UpdateUserProgressRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
