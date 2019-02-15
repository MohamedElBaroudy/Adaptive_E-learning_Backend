package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TeachingRequest",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID"),
        @UniqueConstraint(columnNames = "claimerId")})
@JsonIdentityInfo(
        scope= TeachingRequest.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "requestId")
public class TeachingRequest {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Integer requestId;

    // claimer id
    @NotNull
    @Column(name = "claimerId", unique = true)
    private Integer claimerId;

    // approved?
    @Column(name = "IS_APPROVED")
    private boolean isApproved = false;

    public TeachingRequest() {
    }

    public TeachingRequest(@NotNull Integer claimerId) {
        this.claimerId = claimerId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getClaimerId() {
        return claimerId;
    }

    public void setClaimerId(Integer claimerId) {
        this.claimerId = claimerId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
