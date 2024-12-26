package com.dreamgames.backendengineeringcasestudy.dto.request;

public class AcceptInvitationRequest {
    private Long inviterId;
    private Long invitationId;
    private Long eventId;

    public AcceptInvitationRequest() {}

    public AcceptInvitationRequest(Long inviterId, Long invitationId, Long eventId) {
        this.inviterId = inviterId;
        this.invitationId = invitationId;
        this.eventId = eventId;
    }


    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
