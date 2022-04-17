package com.example.demo.Service;

import com.example.demo.Exceptions.RideOfferNotFoundException;
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
        try {
            ride = offeredRidesRepository.getOfferedRidesByVehicleNo(offeredRide.getVehicleInfo().getVehicleNo());
            if (ride == null){
                throw  new RideOfferNotFoundException("No Rides have been offered by the vehicle - "+offeredRide.getVehicleInfo().getVehicleNo());
            }
        }
        catch (Exception msg){
            System.out.println(msg.getMessage());
        }
        return ride;
    }
    public OfferedRides getOfferedRideByOfferId(Long offerId){
        OfferedRides offeredRide = new OfferedRides();
        try {
            offeredRide = offeredRidesRepository.findByOfferId(offerId);
            if (offeredRide == null){
                throw  new RideOfferNotFoundException("No Ride has been offered by the vehicle ");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return offeredRide;
    }

    public List<OfferedRides> getAllRidesBasedOnSrcAndDestination(String source, String destination, int noOfSeats, boolean mostVacant, String preferredVehicle){
        List<OfferedRides> allRides = new ArrayList<>();
        if( !mostVacant && preferredVehicle.isEmpty()) {
             try {
                 allRides = offeredRidesRepository.getOfferedRidesBySource(source, destination, noOfSeats);
                 if(allRides.size() == 0){
                     throw  new RideOfferNotFoundException("No Rides have been found with Source - " + source + " Destination -" + destination);
                 }
                 else{
                     return allRides;
                 }
             }
             catch (Exception e){
                 System.out.println(e.getMessage());
             }

        }
        else if(mostVacant){
            try {
                allRides = getMostVacantRide(source, destination, noOfSeats);
                if(allRides == null){
                    throw  new RideOfferNotFoundException("No Rides have been found with Source - " + source + " Destination -" + destination + " NoOfSeats - " + noOfSeats);
                }
                else {
                    return allRides;
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }


        }
        else if(!preferredVehicle.isEmpty()){
            try {
                allRides = offeredRidesRepository.getOfferedRidesByVehicleName(source, destination, noOfSeats, preferredVehicle);
                if(allRides == null){
                    throw  new RideOfferNotFoundException("No Rides have been found with Source - " + source + " Destination -" + destination + " Preferred Vehicle - " + preferredVehicle);
                }
                else {
                    return allRides;
                }
            }
            catch (Exception msg){
                System.out.println(msg.getMessage());

            }
        }
        return allRides;

    }


    public String updateRideInfo(Long offerId, OfferedRides updatedRideInfo){

        OfferedRides rideInfo  = new OfferedRides();
        try {

            rideInfo = offeredRidesRepository.findByOfferId(offerId);
            if(rideInfo != null){
                    VehicleModel vehicleInfo = rideInfo.getVehicleInfo();
                    rideInfo.setSource(updatedRideInfo.getSource());
                    rideInfo.setDestination(updatedRideInfo.getDestination());
                    rideInfo.setEnd_ride(updatedRideInfo.isEnd_ride());
                    rideInfo.setVehicleInfo(vehicleInfo);
                    rideInfo.setNo_of_seats(updatedRideInfo.getNo_of_seats());
                    rideInfo.setOfferId(offerId);
                    offeredRidesRepository.save(rideInfo);
                    return rideInfo.toString();
            }
            else {
               throw  new RideOfferNotFoundException("There is no such a ride");
            }
        }
        catch (Exception msg){
            System.out.println(msg.getMessage());
        }
        return rideInfo.toString();

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
            }

        }
        if(!mostVacantRides.isEmpty()){
            res.add(mostVacantRides.get(index));
            return res;
        }

        return res;
    }

}
