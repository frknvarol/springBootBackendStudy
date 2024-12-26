package com.dreamgames.backendengineeringcasestudy.dto.request;

public class GetInvitationsRequest {

    private Long userId;

    public GetInvitationsRequest() {}

    public GetInvitationsRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
