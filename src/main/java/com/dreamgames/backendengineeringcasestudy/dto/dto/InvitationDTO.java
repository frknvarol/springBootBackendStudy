package com.dreamgames.backendengineeringcasestudy.dto.dto;

public class InvitationDTO {
    private Long invitationId;
    private Long inviterId;
    private String inviterUsername;

    public InvitationDTO() {}

    public InvitationDTO(Long invitationId, Long inviterId, String inviterUsername) {
        this.invitationId = invitationId;
        this.inviterId = inviterId;
        this.inviterUsername = inviterUsername;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public String getInviterUsername() {
        return inviterUsername;
    }

    public void setInviterUsername(String inviterUsername) {
        this.inviterUsername = inviterUsername;
    }
}

