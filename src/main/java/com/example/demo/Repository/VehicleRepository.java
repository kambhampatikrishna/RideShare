package com.example.demo.Repository;

import com.example.demo.Models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleModel, Long>
{
    public VehicleModel findByVehicleNo(String vehicleNo);
}
