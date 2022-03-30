package com.example.demo.Repository;

import com.example.demo.Models.RideHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideHistoryRepository extends JpaRepository<RideHistory, Long> {

    public RideHistory findByRideId(Long rideId);

    @Query( value = "SELECT * FROM Ride_History WHERE user_model_userid = :id AND end_ride = true", nativeQuery = true)
    public List<RideHistory> getRideInfoByUserId(@Param("id") Long userId);
}
