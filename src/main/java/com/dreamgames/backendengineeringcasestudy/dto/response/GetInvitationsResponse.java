package com.dreamgames.backendengineeringcasestudy.dto.response;

import com.dreamgames.backendengineeringcasestudy.dto.dto.InvitationDTO;

import java.util.List;

public class GetInvitationsResponse {

    private List<InvitationDTO> invitations;

    public GetInvitationsResponse() {}

    public GetInvitationsResponse(List<InvitationDTO> invitations) {
        this.invitations = invitations;
    }

    public List<InvitationDTO> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<InvitationDTO> invitations) {
        this.invitations = invitations;
    }
}
