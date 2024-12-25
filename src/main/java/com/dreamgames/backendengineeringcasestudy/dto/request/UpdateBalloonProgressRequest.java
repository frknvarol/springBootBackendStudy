package com.dreamgames.backendengineeringcasestudy.dto.request;

public class UpdateBalloonProgressRequest {

    private Long partnershipId;
    private Long userId;
    private int heliumUsed;

    public Long getPartnershipId() {
        return partnershipId;
    }

    public void setPartnershipId(Long partnershipId) {
        this.partnershipId = partnershipId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getHeliumUsed() {
        return heliumUsed;
    }

    public void setHeliumUsed(int heliumUsed) {
        this.heliumUsed = heliumUsed;
    }
}
