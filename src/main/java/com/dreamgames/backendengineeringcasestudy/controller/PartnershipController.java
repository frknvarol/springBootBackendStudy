package com.dreamgames.backendengineeringcasestudy.controller;

import com.dreamgames.backendengineeringcasestudy.dto.request.ClaimRewardRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.GetBalloonsInfoRequest;
import com.dreamgames.backendengineeringcasestudy.dto.request.UpdateBalloonProgressRequest;
import com.dreamgames.backendengineeringcasestudy.dto.response.ClaimRewardResponse;
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

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/partnership")
public class PartnershipController {

    private final PartnershipService partnershipService;

    @Autowired
    public PartnershipController(PartnershipService partnershipService) {
        this.partnershipService = partnershipService;
    }

    @PostMapping("/claim-reward")
    public ResponseEntity<ClaimRewardResponse> claimReward(@RequestBody ClaimRewardRequest request) {
        try {
            ClaimRewardResponse response = partnershipService.claimReward(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return status(HttpStatus.BAD_REQUEST).body(new ClaimRewardResponse("Bad request"));
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
