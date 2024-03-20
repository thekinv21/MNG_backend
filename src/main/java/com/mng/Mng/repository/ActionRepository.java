package com.mng.Mng.repository;

import com.mng.Mng.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ActionRepository extends JpaRepository<Action, String> {

}
