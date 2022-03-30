package com.example.demo.Models;

import javax.persistence.*;

@Entity
@Table(name = "rideHistory")
public class RideHistory {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long rideId;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @ManyToOne
    private UserModel userModel;

    @OneToOne
    private OfferedRides offeredRides;

    @Column(nullable = false)
    private int no_of_seats;

    @Column(nullable = false)
    private boolean end_ride = false;

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public int getNo_of_seats() {
        return no_of_seats;
    }

    public void setNo_of_seats(int no_of_seats) {
        this.no_of_seats = no_of_seats;
    }

    public boolean isEnd_ride() {
        return end_ride;
    }

    public void setEnd_ride(boolean end_ride) {
        this.end_ride = end_ride;
    }


    public OfferedRides getOfferedRides() {
        return offeredRides;
    }

    public void setOfferedRides(OfferedRides offeredRides) {
        this.offeredRides = offeredRides;
    }



    @Override
    public String toString() {
        return "RideHistory{" +
                "rideId=" + rideId +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", userModel=" + userModel +
                ", no_of_seats=" + no_of_seats +
                ", end_ride=" + end_ride +
                '}';
    }
}
