package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.dto.request.ClaimRewardRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetBalloonsInfoRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.UpdateBalloonProgressRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.GetBalloonsInfoResponse;
import com.dreamgames.backendengineeringcasestudy.dto.response.UpdateBalloonProgressResponse;
import com.dreamgames.backendengineeringcasestudy.model.Partnership;
import com.dreamgames.backendengineeringcasestudy.repository.PartnershipRepository;
import com.dreamgames.backendengineeringcasestudy.service.PartnershipService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partnership")
public class PartnershipController {

    private final PartnershipService partnershipService;
    private final PartnershipRepository partnershipRepository;

    @Autowired
    public PartnershipController(PartnershipService partnershipService, PartnershipRepository partnershipRepository) {
        this.partnershipRepository = partnershipRepository;
        this.partnershipService = partnershipService;
    }

    @PostMapping("/claim-reward")
    public ResponseEntity<String> claimReward(@RequestBody ClaimRewardRequest request) {
        Partnership partnership = partnershipRepository.findById(request.getPartnershipId())
                .orElseThrow(() -> new RuntimeException("Partnership not found"));

        try {
            partnershipService.claimReward(partnership);
            return ResponseEntity.ok("Reward claimed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateBalloonProgress")
    @ResponseStatus(HttpStatus.OK)
    public UpdateBalloonProgressResponse updateBalloonProgress(@RequestBody UpdateBalloonProgressRequest request) {
        return partnershipService.updateBalloonProgress(request);
    }

    @GetMapping("/balloons-info")
    public ResponseEntity<GetBalloonsInfoResponse> getBalloonsInfo(@Valid @RequestBody GetBalloonsInfoRequest request) {
        GetBalloonsInfoResponse response = partnershipService.getBalloonsInfo(request);
        return ResponseEntity.ok(response);
    }
}
