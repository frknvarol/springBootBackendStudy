package com.dreamgames.backendengineeringcasestudy.dto.response;

import com.dreamgames.backendengineeringcasestudy.dto.dto.InvitationDTO;

import java.util.List;

public class InvitationsResponse {

    private List<InvitationDTO> invitations;

    public InvitationsResponse() {}

    public InvitationsResponse(List<InvitationDTO> invitations) {
        this.invitations = invitations;
    }

    public List<InvitationDTO> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<InvitationDTO> invitations) {
        this.invitations = invitations;
    }
}
