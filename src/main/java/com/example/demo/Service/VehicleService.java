package com.example.demo.Service;

import com.example.demo.Models.VehicleModel;
import com.example.demo.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public  VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleModel> getVehiclesRegistered(){
        return vehicleRepository.findAll();
    }

    public String addVehicle(VehicleModel vehicle){
        vehicleRepository.save(vehicle);
        return "Saved....";
    }

    public VehicleModel getVehicleByVehicleNo(String vehNo){
        VehicleModel vehicleInfo = vehicleRepository.findByVehicleNo(vehNo);
        return vehicleInfo;
    }
}
