package com.example.kaf.notify.repository;

import com.example.kaf.notify.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
    List<Log> findByType(String type);
    List<Log> findByEntityId(String entityId);
}

