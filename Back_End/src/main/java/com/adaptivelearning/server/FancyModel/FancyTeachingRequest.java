package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.TeachingRequest;
import com.adaptivelearning.server.Model.User;

import java.util.LinkedList;
import java.util.List;

public class FancyTeachingRequest {
    // id
    private Integer requestId;

    // claimer id
    private Long claimerId;

    // claimer name
    private String claimerName;

    // claimer email
    private String claimerEmail;

    // is approved
    boolean isApproved;

    public FancyTeachingRequest() {
    }

    public FancyTeachingRequest toFancyTeachingRequestMapping(TeachingRequest teachingRequest){
        this.requestId = teachingRequest.getRequestId();
        this.claimerId = teachingRequest.getClaimerId();
        this.isApproved = teachingRequest.isApproved();
        this.claimerName = teachingRequest.getName();
        this.claimerEmail = teachingRequest.getEmail();
        return this;
    }

    public List<FancyTeachingRequest> toFancyTeachingRequestListMapping(List<TeachingRequest> teachingRequests){
        List<FancyTeachingRequest> fancyTeachingRequestList = new LinkedList<>();
        for (TeachingRequest teachingRequest:
                teachingRequests) {
            FancyTeachingRequest fancyTeachingRequest = new FancyTeachingRequest();
            ((LinkedList<FancyTeachingRequest>) fancyTeachingRequestList).addLast(
                    fancyTeachingRequest.toFancyTeachingRequestMapping(teachingRequest));
        }
        return fancyTeachingRequestList;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Long getClaimerId() {
        return claimerId;
    }

    public void setClaimerId(Long claimerId) {
        this.claimerId = claimerId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getClaimerName() {
        return claimerName;
    }

    public void setClaimerName(String claimerName) {
        this.claimerName = claimerName;
    }

    public String getClaimerEmail() {
        return claimerEmail;
    }

    public void setClaimerEmail(String claimerEmail) {
        this.claimerEmail = claimerEmail;
    }
}
