package com.example.demo.Models;

import com.example.demo.Models.UserModel;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
public class VehicleModel {


    @Column(nullable = false)
    private String vehicleName;

    @Id
    @Column(name = "VEHICLE_NO", nullable = false,columnDefinition="VARCHAR(64)")
    private String vehicleNo;

    @ManyToOne
    private UserModel userModel;

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public String toString() {
        return "VehicleModel{" +
                ", vehicleName='" + vehicleName + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", userModel=" + userModel +
                '}';
    }
}
