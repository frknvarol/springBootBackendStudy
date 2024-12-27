package com.dreamgames.backendengineeringcasestudy.dto.response;

public class ClaimRewardResponse {

    private String message;

    public ClaimRewardResponse() {}

    public ClaimRewardResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
