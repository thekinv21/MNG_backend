package com.mng.Mng.repository;

import com.mng.Mng.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, String> {
    Optional<Office> findByOfficeName(String officeName);
    Optional<Office> findOfficeById(String id);
}
