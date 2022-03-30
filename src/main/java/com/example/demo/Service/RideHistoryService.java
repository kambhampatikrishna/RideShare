package com.example.demo.Service;

import com.example.demo.Models.OfferedRides;
import com.example.demo.Models.RideHistory;
import com.example.demo.Models.UserModel;
import com.example.demo.Repository.RideHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideHistoryService {

    private final RideHistoryRepository rideHistoryRepository;

    @Autowired
    public RideHistoryService(RideHistoryRepository rideHistoryRepository){
        this.rideHistoryRepository = rideHistoryRepository;
    }

    public RideHistory addRideHistory(RideHistory rideHistory)
    {
        rideHistoryRepository.save(rideHistory);
        return rideHistory;
    }

    public List<RideHistory> getRideHistory(){
        return rideHistoryRepository.findAll();
    }

    public RideHistory getRideInfoById(Long rideId){
        return rideHistoryRepository.findByRideId(rideId);
    }

    public List<RideHistory> getRideInfoByUserId(Long userId){
        return rideHistoryRepository.getRideInfoByUserId(userId);
    }

}
