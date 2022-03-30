package com.example.demo.Service;

import com.example.demo.Models.OfferedRides;
import com.example.demo.Models.UserModel;
import com.example.demo.Models.VehicleModel;
import com.example.demo.Repository.OfferedRidesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferedRidesService {

    private final OfferedRidesRepository offeredRidesRepository;

    @Autowired
    public OfferedRidesService(OfferedRidesRepository offeredRidesRepository) {
        this.offeredRidesRepository = offeredRidesRepository;
    }

    public List<OfferedRides> findAllOfferedRides(){ return offeredRidesRepository.findAll();}

    public String addRideOffer(OfferedRides offeredRide, UserModel userModel, VehicleModel vehicleInfo){

        List<OfferedRides> previousRideOffer = getOfferedRideByVehicleNo(offeredRide);
        if(previousRideOffer.size() == 0) {
            int count = userModel.getOffered_count();
            userModel.setTaken_count(count+1);
            vehicleInfo.setUserModel(userModel);
            offeredRide.setVehicleInfo(vehicleInfo);
            offeredRidesRepository.save(offeredRide);
            return offeredRide.toString();
        }

        return "Ride already offered by vehicle";

    }

    public List<OfferedRides> getOfferedRideByVehicleNo(OfferedRides offeredRide){
        List<OfferedRides> ride = new ArrayList<>();
        ride = offeredRidesRepository.getOfferedRidesByVehicleNo(offeredRide.getVehicleInfo().getVehicleNo());
        return ride;
    }
    public OfferedRides getOfferedRideByOfferId(Long offerId){
        OfferedRides offeredRide = offeredRidesRepository.findByOfferId(offerId);
        return offeredRide;
    }

    public List<OfferedRides> getAllRidesBasedOnSrcAndDestination(String source, String destination, int noOfSeats, boolean mostVacant, String preferredVehicle){
        List<OfferedRides> allRides = new ArrayList<>();
        if( !mostVacant && preferredVehicle == null) {
             allRides = offeredRidesRepository.getOfferedRidesBySource(source, destination, noOfSeats);
             System.out.println("Normal");
        }
        else if(mostVacant){
            allRides = getMostVacantRide(source, destination, noOfSeats);
            System.out.println("Most Vacant");

        }
        else if(preferredVehicle != null){
            allRides = offeredRidesRepository.getOfferedRidesByVehicleName(source, destination, noOfSeats, preferredVehicle);
            System.out.println("No of Seats");
        }
        return allRides;

    }


    public void updateRideInfo(Long offerId, OfferedRides updatedRideInfo){

        OfferedRides rideInfo = offeredRidesRepository.findByOfferId(offerId);
        VehicleModel vehicleInfo = rideInfo.getVehicleInfo();
        if(rideInfo != null){
            rideInfo.setSource(updatedRideInfo.getSource());
            rideInfo.setDestination(updatedRideInfo.getDestination());
            rideInfo.setEnd_ride(updatedRideInfo.isEnd_ride());
            rideInfo.setVehicleInfo(vehicleInfo);
            rideInfo.setNo_of_seats(updatedRideInfo.getNo_of_seats());
            rideInfo.setOfferId(offerId);
            offeredRidesRepository.save(rideInfo);
        }
    }

    public List<OfferedRides> getMostVacantRide(String source, String destination, int noOfSeats){
        int max = -1;
        int index = 0;
        List<OfferedRides>mostVacantRides = new ArrayList<>();
        List<OfferedRides> res = new ArrayList<>();
        mostVacantRides = offeredRidesRepository.getOfferedRidesBySource(source, destination, noOfSeats);
        for(int i=0;i<mostVacantRides.size();i++){
            int no_of_seats = mostVacantRides.get(i).getNo_of_seats();

            if(no_of_seats >= max){
                max = no_of_seats;
                index = i;
                System.out.println(max+  " seats: "+ no_of_seats);
            }

        }
        if(!mostVacantRides.isEmpty()){
            res.add(mostVacantRides.get(index));
            return res;
        }

        return res;
    }

}
