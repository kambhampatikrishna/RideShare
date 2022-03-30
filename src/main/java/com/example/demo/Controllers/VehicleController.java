package com.example.demo.Controllers;

import com.example.demo.Models.UserModel;
import com.example.demo.Models.VehicleModel;
import com.example.demo.Service.UserService;
import com.example.demo.Service.VehicleService;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(path ="/api/v0/vehicle/")
public class VehicleController {

    private final VehicleService vehicleService;

    private UserService userService;


    @Autowired
    public VehicleController(VehicleService vehicleService, UserService userService){
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    @GetMapping(path = "/all")
    public String getAll(){
        List<VehicleModel>users = vehicleService.getVehiclesRegistered();
        if(users.size() > 0)
            return users.toString();
        else
            return "No Vehicle has been registered yet";
    }


    @GetMapping(path = "/vehicleNum = {vehicleNo}")
    public String getVehicleByVehicleNo(@PathVariable("vehicleNo") String VehicleNo){
        try {

            VehicleModel vehicleInfo = vehicleService.getVehicleByVehicleNo(VehicleNo);
            if(vehicleInfo != null) {
                return vehicleInfo.toString();
            }
            else {
                return "No Vehicle Added with %s"+VehicleNo;
            }
        }
        catch (Exception msg) {
            msg.printStackTrace();
        }
        return "Not found";

    }

    @PostMapping(path = "/addVehicle/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public VehicleModel saveVehicle(@RequestBody VehicleModel vehicle, @PathVariable("userId") Long userId){
        try {
            UserModel userInfo = userService.getUserById(userId);
            if(userInfo != null) {
                vehicle.setUserModel(userInfo);
                vehicleService.addVehicle(vehicle);
                return vehicle;
            }
            else{
                throw new NullPointerException("User not Found");
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
        return vehicle;
    }


}
