package com.dreamgames.backendengineeringcasestudy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class InvitePartnerResponse {

    @JsonProperty("invitationId")
    private Long invitationId;

    @JsonProperty("message")
    private String message;

    public InvitePartnerResponse() {}

    public InvitePartnerResponse(String message, Long invitationId) {
        this.message = message;
        this.invitationId = invitationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }
}
