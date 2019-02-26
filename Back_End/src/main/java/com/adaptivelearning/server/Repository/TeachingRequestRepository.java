package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.TeachingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachingRequestRepository extends JpaRepository<TeachingRequest,Integer> {
    boolean existsByClaimerId(Long claimerId);
    boolean existsByClaimerIdAndIsApproved(Long claimerId,boolean isApproved);

    TeachingRequest findByClaimerId(Long claimerId);
}
