package com.dreamgames.backendengineeringcasestudy.dto.request;


public class InvitePartnerRequest {
    private Long inviterId;
    private Long invitedId;
    private Long eventId;

    public InvitePartnerRequest() {}

    public InvitePartnerRequest(Long inviterId, Long invitedId, Long eventId) {
        this.inviterId = inviterId;
        this.invitedId = invitedId;
        this.eventId = eventId;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public Long getInvitedId() {
        return invitedId;
    }

    public void setInvitedId(Long invitedId) {
        this.invitedId = invitedId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

}
