package com.example.demo.Service;

import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Exceptions.VehicleNotFoundException;
import com.example.demo.Models.UserModel;
import com.example.demo.Models.VehicleModel;
import com.example.demo.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    private UserService userService;

    @Autowired
    public  VehicleService(VehicleRepository vehicleRepository, UserService userService){
        this.vehicleRepository = vehicleRepository;
        this.userService = userService;
    }

    public String getVehiclesRegistered(){
        List<VehicleModel>vehicles = vehicleRepository.findAll();
        if(vehicles.size() > 0)
            return vehicles.toString();
        else
            return "No Vehicle has been registered yet";
    }

    public VehicleModel addVehicle(VehicleModel vehicle, Long userId){

        try {
            UserModel userInfo = userService.findUserById(userId);
            if(userInfo != null) {
                vehicle.setUserModel(userInfo);
                vehicleRepository.save(vehicle);
            }
            else{
                throw new UserNotFoundException("User not Found");
            }

        }
        catch (Exception e){
            System.out.println(e);
        }

        return vehicle;
    }


    public String findVehicleByVehicleNo(String vehNo){
        VehicleModel vehicleInfo = new VehicleModel();
        try {
            vehicleInfo = getVehicleByVehicleNo(vehNo);
            if(vehicleInfo == null) {
                throw new VehicleNotFoundException("No Vehicle Added with "+vehNo);
            }
        }
        catch (Exception msg) {
            System.out.println(msg.getMessage());
        }
        return vehicleInfo.toString();

    }

    public VehicleModel getVehicleByVehicleNo(String vehNo){
        VehicleModel vehicleInfo = new VehicleModel();
        try {
            vehicleInfo = vehicleRepository.findByVehicleNo(vehNo);
            if(vehicleInfo != null) {
                return vehicleInfo;
            }
            else {
                throw new VehicleNotFoundException("No Vehicle Added with %s"+vehNo);
            }
        }
        catch (Exception msg){
            System.out.println(msg.getMessage());
        }
        return vehicleInfo;
    }
}
