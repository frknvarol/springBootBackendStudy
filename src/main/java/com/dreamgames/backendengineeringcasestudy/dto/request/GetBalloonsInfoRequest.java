package com.dreamgames.backendengineeringcasestudy.dto.request;

import jakarta.validation.constraints.NotNull;

public class GetBalloonsInfoRequest {

    @NotNull(message = "Partnership ID must not be null")
    private Long partnershipId;

    public GetBalloonsInfoRequest() {
    }

    public GetBalloonsInfoRequest(Long partnershipId) {
        this.partnershipId = partnershipId;
    }

    public Long getPartnershipId() {
        return partnershipId;
    }

    public void setPartnershipId(Long partnershipId) {
        this.partnershipId = partnershipId;
    }
}
