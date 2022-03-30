package com.example.demo.Controllers;

import com.example.demo.Models.OfferedRides;
import com.example.demo.Models.UserModel;
import com.example.demo.Models.VehicleModel;
import com.example.demo.Service.OfferedRidesService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v0/offered_rides")
public class OfferedRidesController {

    private final OfferedRidesService offeredRidesService;

    private UserService userService;

    private VehicleService vehicleService;


    @Autowired
    public OfferedRidesController(OfferedRidesService offeredRidesService, UserService userService, VehicleService vehicleService) {
        this.offeredRidesService = offeredRidesService;
        this.userService =  userService;
        this.vehicleService =  vehicleService;
    }

    @GetMapping(path = "/{offerId}")
    public OfferedRides getOfferedRide(@PathVariable("offerId") Long offerId){
       OfferedRides ride_offer = offeredRidesService.getOfferedRideByOfferId(offerId);
       return ride_offer;
    }

    @GetMapping(path = "/{userId}/getAllRideOffers")
    public List<OfferedRides> getOfferBasedOnQuery(@RequestParam String source,
                                     @RequestParam String destination,
                                     @RequestParam int noOfSeats,
                                     @RequestParam(required = false) Boolean mostVacant,
                                     @RequestParam(required = false) String preferredVehicle,
                                     @PathVariable Long userId){

        List<OfferedRides> offerIdsList = offeredRidesService.getAllRidesBasedOnSrcAndDestination(source, destination, noOfSeats, mostVacant, preferredVehicle);
        return offerIdsList;
    }

    @PostMapping(path = "/offer/{vehicleNo}/{userId}")
    public String rideOfferedByUser(@RequestBody OfferedRides offeredRide, @PathVariable("vehicleNo") String vehicleNo, @PathVariable("userId") Long userId){

            String res;
            UserModel userInfo = userService.getUserById(userId);
            VehicleModel vehicleModel = vehicleService.getVehicleByVehicleNo(vehicleNo);
            res = offeredRidesService.addRideOffer(offeredRide, userInfo, vehicleModel);
            return res;

    }

    @PutMapping(path = "/change/{offerId}")
    public OfferedRides changeRideInfo(@RequestBody OfferedRides offeredRide , @PathVariable("offerId") Long offerId){
        OfferedRides rideInfo = getOfferedRide(offerId);
        if(rideInfo != null){
            VehicleModel vehicleInfo = rideInfo.getVehicleInfo();
            offeredRide.setVehicleInfo(vehicleInfo);
            offeredRide.setOfferId(offerId);
            offeredRidesService.updateRideInfo(offerId,offeredRide);
        }
        else{
            System.out.println("There is no such a ride");
        }
        return offeredRide;
    }





}
