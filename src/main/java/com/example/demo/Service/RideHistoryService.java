package com.example.demo.Service;

import com.example.demo.Exceptions.RideHistoryNotFoundException;
import com.example.demo.Exceptions.RideOfferNotFoundException;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Models.OfferedRides;
import com.example.demo.Models.RideHistory;
import com.example.demo.Models.UserModel;
import com.example.demo.Repository.RideHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideHistoryService {

    private final RideHistoryRepository rideHistoryRepository;

    private final OfferedRidesService offeredRidesService;

    private final UserService userService;

    @Autowired
    public RideHistoryService(RideHistoryRepository rideHistoryRepository, OfferedRidesService offeredRidesService, UserService userService){
        this.rideHistoryRepository = rideHistoryRepository;
        this.offeredRidesService = offeredRidesService;
        this.userService = userService;
    }

    public RideHistory addRideHistory(String source, String destination, int noOfSeats, Long userId, Long offerId)
    {

        RideHistory rideHistory = new RideHistory();
        try {
            UserModel userInfo = userService.findUserById(userId);
            OfferedRides offeredRide = offeredRidesService.getOfferedRideByOfferId(offerId);
            if(userInfo != null && offeredRide != null) {
                rideHistory.setSource(source);
                rideHistory.setDestination(destination);
                rideHistory.setNo_of_seats(noOfSeats);
                rideHistory.setUserModel(userInfo);
                rideHistory.setOfferedRides(offeredRide);
                rideHistory.setEnd_ride(false);
                offeredRide.setEnd_ride(true);
                rideHistoryRepository.save(rideHistory);
            }
            else if(userInfo == null){
                throw new UserNotFoundException(" User not Found ");
            }
            else {
                throw  new RideOfferNotFoundException(" Ride not found ");
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return rideHistory;
    }

    public List<RideHistory> getRideHistory(){
        return rideHistoryRepository.findAll();
    }

    public RideHistory getRideInfoById(Long rideId){
        RideHistory rideHistory = new RideHistory();
        try {
             rideHistory = rideHistoryRepository.findByRideId(rideId);
             if(rideHistory == null){
                 throw new RideHistoryNotFoundException("No Rides have taken till now");
             }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
     return rideHistory;
    }

    public String getRideInfoByUserId(Long userId){
        List<RideHistory>historyList = new ArrayList<>();
        try{
            historyList = rideHistoryRepository.getRideInfoByUserId(userId);
            if(historyList.size()==0) {
                return "No Rides have taken till now";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return historyList.toString();

    }

    public String updateRideHistory(RideHistory rideHistory){
        try {
            rideHistoryRepository.save(rideHistory);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return "updated";


    }

    public String endRide(Long rideId){
        RideHistory rideHistory = getRideInfoById(rideId);
        OfferedRides offeredRide = rideHistory.getOfferedRides();
        UserModel userModel = userService.findUserById(rideHistory.getUserModel().getUserID());
        try {
            if(userModel == null){
                throw new UserNotFoundException("User not found");
            }
            rideHistory.setEnd_ride(true);
            offeredRide.setEnd_ride(true);
            userModel.setTaken_count(userModel.getTaken_count()+1);
            offeredRidesService.updateRideInfo(offeredRide.getOfferId(), offeredRide);
            updateRideHistory(rideHistory);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "Ride Ended";
    }
}
