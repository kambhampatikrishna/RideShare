package com.example.demo.Controllers;

import com.example.demo.Models.OfferedRides;
import com.example.demo.Models.RideHistory;
import com.example.demo.Models.UserModel;
import com.example.demo.Service.OfferedRidesService;
import com.example.demo.Service.RideHistoryService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v0/rideHistory")
public class RideHistoryController {

    private RideHistoryService historyService;

    private UserService userService;

    private OfferedRidesService offeredRidesService;

    @Autowired
    public RideHistoryController(RideHistoryService historyService, UserService userService, OfferedRidesService offeredRidesService){
        this.historyService = historyService;
        this.userService = userService;
        this.offeredRidesService = offeredRidesService;
    }

    @GetMapping(path = "/all")
    public String getAll(){
        List<RideHistory> rideHistoryList = historyService.getRideHistory();
        if(rideHistoryList.size() > 0)
            return rideHistoryList.toString();
        else
            return "No Ride have been taken";
    }

    @GetMapping(path = "/getRideInfoByRideId/{rideId}")
    public String getRideInfo(@PathVariable("rideId") Long rideId){
        RideHistory rideInfo = new RideHistory();
        rideInfo = historyService.getRideInfoById(rideId);
        return rideInfo.toString();

    }

    @GetMapping(path = "/{userId}/getUserRideInfo")
    public String getRideInfoByUserId(@PathVariable Long userId){
       String response = historyService.getRideInfoByUserId(userId);
        return response;
    }

    @PostMapping(path = "/{userId}/{offerId}/selectRide")
    public RideHistory saveRideInfo(@RequestParam String source,
                             @RequestParam String destination,
                             @RequestParam int noOfSeats,
                                    @PathVariable("userId") Long userId,
                             @PathVariable("offerId") Long offerId){

             RideHistory rideHistory  =   historyService.addRideHistory(source,destination, noOfSeats, userId, offerId);
              return rideHistory;
    }

    @PutMapping(path = "/{rideId}/endRide")
    public String endRide(@PathVariable("rideId") Long rideId) {
        String response = historyService.endRide(rideId);
        return response;

    }
}
