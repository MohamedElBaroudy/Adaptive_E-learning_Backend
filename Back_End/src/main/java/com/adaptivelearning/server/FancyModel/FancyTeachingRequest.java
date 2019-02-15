package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.TeachingRequest;

import java.util.LinkedList;
import java.util.List;

public class FancyTeachingRequest {
    // id
    private int requestId;

    // claimer id
    private int claimerId;

    // is approved
    boolean isApproved;

    public FancyTeachingRequest() {
    }

    public FancyTeachingRequest toFancyTeachingRequestMapping(TeachingRequest teachingRequest){
        this.requestId = teachingRequest.getRequestId();
        this.claimerId = teachingRequest.getClaimerId();
        this.isApproved = teachingRequest.isApproved();
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

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getClaimerId() {
        return claimerId;
    }

    public void setClaimerId(int claimerId) {
        this.claimerId = claimerId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
