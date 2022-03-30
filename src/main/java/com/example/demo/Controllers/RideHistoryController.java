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

    @GetMapping(path = "/{rideId}/getRideInfoByRideId")
    public String getRideInfo(@PathVariable("rideId") Long rideId){
        RideHistory rideInfo = new RideHistory();
        try{
            rideInfo = historyService.getRideInfoById(rideId);
            if(rideInfo == null) {
                return "No Rides have taken till now";
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return rideInfo.toString();

    }

    @GetMapping(path = "/{userId}/getUserRideInfo")
    public String getRideInfoByUserId(@PathVariable Long userId){
        List<RideHistory>historyList = new ArrayList<>();
        try{
            historyList = historyService.getRideInfoByUserId(userId);
            if(historyList.size()==0) {
                return "No Rides have taken till now";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return historyList.toString();
    }

    @PostMapping(path = "/{userId}/{offerId}/selectRide")
    public RideHistory saveRideInfo(@RequestParam String source,
                             @RequestParam String destination,
                             @RequestParam int noOfSeats,
                                    @PathVariable("userId") Long userId,
                             @PathVariable("offerId") Long offerId){
        RideHistory rideHistory = new RideHistory();
        try {
            UserModel userInfo = userService.getUserById(userId);
            OfferedRides offeredRide = offeredRidesService.getOfferedRideByOfferId(offerId);
            if(userInfo != null && offeredRide != null) {
                rideHistory.setSource(source);
                rideHistory.setDestination(destination);
                rideHistory.setNo_of_seats(noOfSeats);
                rideHistory.setUserModel(userInfo);
                rideHistory.setOfferedRides(offeredRide);
                rideHistory.setEnd_ride(false);
                offeredRide.setEnd_ride(true);
                historyService.addRideHistory(rideHistory);
            }
            else{
                throw new NullPointerException("User not Found or ride not found");
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
        return rideHistory;
    }

    @PutMapping(path = "/{rideId}/endRide")
    public String endRide(@PathVariable("rideId") Long rideId){
        RideHistory rideHistory = historyService.getRideInfoById(rideId);
        OfferedRides offeredRide = rideHistory.getOfferedRides();
        UserModel userModel = userService.getUserById(rideHistory.getUserModel().getUserID());
        try {
                rideHistory.setEnd_ride(true);
                offeredRide.setEnd_ride(true);
                userModel.setTaken_count(userModel.getTaken_count()+1);
                offeredRidesService.updateRideInfo(offeredRide.getOfferId(), offeredRide);
                historyService.addRideHistory(rideHistory);

        }
        catch (Exception e){
            System.out.println(e);
        }
        return "Ride Ended";
    }
}
