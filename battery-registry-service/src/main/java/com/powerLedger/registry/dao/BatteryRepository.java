package com.powerLedger.registry.dao;

import com.powerLedger.registry.dao.Entity.Battery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {
    /**
     * Return list battery list which exist within the post code range
     * @param fromPostCode min post code
     * @param toPostCode max post code
     * @param sort Sorting parameter and order
     * @return List battery list which exist within the post code range
     */
    List<Battery> findBatteriesByPostCodeBetween(Integer fromPostCode, Integer toPostCode, Sort sort);

    /**
     * Return list of batteries where the capacity is under given value
     * @param capacity max capacity
     * @return List battery list
     */
    @Query(value = "select * from battery where watt_capacity < ?1", nativeQuery = true )
    List<Battery> findBatteriesBelowWattCapacity(Integer capacity);
}
