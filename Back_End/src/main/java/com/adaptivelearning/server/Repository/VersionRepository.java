package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version,Long> {
    Version findByVersionId(Long versionId);
}
