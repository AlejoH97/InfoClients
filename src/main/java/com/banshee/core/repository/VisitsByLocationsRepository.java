package com.banshee.core.repository;

import com.banshee.core.entity.VisitsPerCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitsByLocationsRepository extends JpaRepository<VisitsPerCity, Long> {

    @Query(
            value = "SELECT CITY, SUM(VISITS) AS VISITS FROM (SELECT * FROM LOCATION INNER JOIN (CLIENT_LOCATION) ON LOCATION.ID = CLIENT_LOCATION.LOCATION_ID) T1 LEFT JOIN (SELECT ID, VISITS FROM CLIENT INNER JOIN (SELECT CLIENT_ID, COUNT(*) AS VISITS  FROM VISIT GROUP BY CLIENT_ID) VISITS ON CLIENT.ID = VISITS.CLIENT_ID) T2 ON T1.CLIENT_ID = T2.ID GROUP BY CITY", nativeQuery = true)
    List<VisitsPerCity> getVisitsByLocations();
}
