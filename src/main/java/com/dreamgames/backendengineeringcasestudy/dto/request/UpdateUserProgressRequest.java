package com.dreamgames.backendengineeringcasestudy.dto.request;


public class UpdateUserProgressRequest {
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
