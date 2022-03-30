package com.example.demo.Repository;


import com.example.demo.Models.OfferedRides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferedRidesRepository extends JpaRepository<OfferedRides, Long> {

    public OfferedRides findByOfferId(Long offerId);

    @Query( value = "SELECT * from Offered_Rides WHERE source= :s AND destination= :d AND no_of_seats>= :no AND end_ride = false" , nativeQuery = true)
    public List<OfferedRides> getOfferedRidesBySource(@Param("s") String source,
                                              @Param("d") String destination,
                                              @Param("no") int noOfSeats);

    @Query( value = "SELECT * from Offered_Rides off INNER JOIN riders.Vehicle v ON off.vehicle_info_vehicle_no = v.vehicle_no WHERE off.source= :s AND off.destination= :d AND off.no_of_seats= :no AND v.vehicle_name= :name" , nativeQuery = true)
    public List<OfferedRides> getOfferedRidesByVehicleName(@Param("s") String source,
                                                           @Param("d") String destination,
                                                           @Param("no") int noOfSeats,
                                                           @Param("name") String vehicleName);

    @Query( value = "SELECT * from Offered_Rides off INNER JOIN riders.Vehicle v ON off.vehicle_info_vehicle_no = v.vehicle_no WHERE v.vehicle_no= :no AND off.end_ride = false" , nativeQuery = true)
    public List<OfferedRides> getOfferedRidesByVehicleNo(@Param("no") String vehicleNo);

}
