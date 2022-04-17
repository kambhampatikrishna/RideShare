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
        String users = vehicleService.getVehiclesRegistered();
        return users;
    }


    @GetMapping(path = "/vehicleNum = {vehicleNo}")
    public String getVehicleByVehicleNo(@PathVariable("vehicleNo") String VehicleNo){
        String vehicle = vehicleService.findVehicleByVehicleNo(VehicleNo);
        return vehicle;
    }

    @PostMapping(path = "/addVehicle/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public VehicleModel saveVehicle(@RequestBody VehicleModel vehicle, @PathVariable("userId") Long userId){

        VehicleModel saved_vehicle = vehicleService.addVehicle(vehicle, userId);

        return saved_vehicle;
    }


}
