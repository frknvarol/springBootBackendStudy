package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.dto.request.GetInvitationsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.InvitePartnerRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitationsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitePartnerResponse;
import com.dreamgames.backendengineeringcasestudy.service.InvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/invite")
    public ResponseEntity<InvitePartnerResponse> invitePartner(@RequestBody InvitePartnerRequest request) {

        try {
            InvitePartnerResponse response = invitationService.invitePartner(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InvitePartnerResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/received")
    public ResponseEntity<InvitationsResponse> getReceivedInvitations(@RequestBody GetInvitationsRequest request) {
        InvitationsResponse response = invitationService.getReceivedInvitations(request);
        return ResponseEntity.ok(response);
    }
}
