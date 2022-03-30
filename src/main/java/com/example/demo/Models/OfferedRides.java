package com.example.demo.Models;


import javax.persistence.*;

@Entity
@Table(name = "offered_rides")
public class OfferedRides {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id", nullable = false)
    private Long offerId;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @ManyToOne
    private VehicleModel vehicleInfo;

    @Column(nullable = false)
    private int no_of_seats;

    @Column(nullable = false)
    private boolean end_ride = false;

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
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

    public VehicleModel getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(VehicleModel vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
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

    @Override
    public String toString() {
        return "OfferedRides{" +
                "offerId=" + offerId +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", vehicleInfo=" + vehicleInfo +
                ", no_of_seats=" + no_of_seats +
                ", end_ride=" + end_ride +
                '}';
    }
}

