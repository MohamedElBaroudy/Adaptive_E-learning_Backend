package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private Long claimerId;

    @NotBlank
    @Size(max = 40)
    @Column(name = "CLAIMER_NAME")
    private String name;

    @NotBlank
    @Size(max = 40)
    @Column(name = "CLAIMER_EMAIL")
    private String email;

    // approved?
    @Column(name = "IS_APPROVED")
    private boolean isApproved = false;

    public TeachingRequest() {
    }

    public TeachingRequest(@NotNull Long claimerId,
                           @NotBlank @Size(max = 40) String name,
                           @NotBlank @Size(max = 40) String email) {
        this.claimerId = claimerId;
        this.name = name;
        this.email = email;
    }

    public Integer getRequestId() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
