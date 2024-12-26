package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.dto.request.GetInvitationsRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.InvitationsResponse;
import com.dreamgames.backendengineeringcasestudy.service.InvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/received")
    public ResponseEntity<InvitationsResponse> getReceivedInvitations(
            @RequestBody GetInvitationsRequest request) {
        InvitationsResponse response = invitationService.getReceivedInvitations(request);
        return ResponseEntity.ok(response);
    }
}
