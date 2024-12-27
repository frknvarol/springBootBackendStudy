package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.dto.request.AcceptInvitationRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetInvitationsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.InvitePartnerRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.RejectInvitationRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.AcceptInvitationResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetInvitationsResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitePartnerResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.RejectInvitationResponse;
import com.dreamgames.backendengineeringcasestudy.service.InvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

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
            return status(HttpStatus.BAD_REQUEST).body(new InvitePartnerResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/received")
    public ResponseEntity<GetInvitationsResponse> getReceivedInvitations(@RequestBody GetInvitationsRequest request) {
        GetInvitationsResponse response = invitationService.getReceivedInvitations(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accept-invitation")
    public ResponseEntity<AcceptInvitationResponse> acceptInvitation(@RequestBody AcceptInvitationRequest request) {
        AcceptInvitationResponse response = invitationService.acceptInvitation(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reject-invitation")
    public ResponseEntity<RejectInvitationResponse> rejectInvitation(@RequestBody RejectInvitationRequest request) {
        RejectInvitationResponse response = invitationService.rejectInvitation(request);
        return ResponseEntity.ok(response);
    }
}
