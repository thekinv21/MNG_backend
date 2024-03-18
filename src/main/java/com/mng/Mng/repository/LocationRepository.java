package com.mng.Mng.repository;

import com.mng.Mng.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findLocationById(Integer id);
}
