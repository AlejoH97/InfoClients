package com.banshee.core.repository;

import com.banshee.core.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query(
            value = "SELECT v FROM Visit v WHERE v.client.id = ?1")
    List<Visit> findVisitByClients(long id);
}
