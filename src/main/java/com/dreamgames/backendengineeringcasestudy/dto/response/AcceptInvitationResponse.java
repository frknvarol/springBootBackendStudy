package com.dreamgames.backendengineeringcasestudy.dto.response;

public class AcceptInvitationResponse {

    private Long invitationId;
    private String message;

    public AcceptInvitationResponse() {}

    public AcceptInvitationResponse(Long invitationId, String message) {
        this.invitationId = invitationId;
        this.message = message;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
