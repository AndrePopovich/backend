package com.bs.repository;

import com.bs.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    @Query(value = "SELECT * FROM ads_table ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Ad> findRandomAds(@Param("limit") int limit);
    List<Ad> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String key1, String key2);


}
