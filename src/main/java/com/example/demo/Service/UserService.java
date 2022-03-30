package com.example.demo.Service;

import com.example.demo.Models.UserModel;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getUsers(){
        return userRepository.findAll();
    }

    public String addUser(UserModel user){
        userRepository.save(user);
        return "Saved....";
    }

    public UserModel getUserById(Long id){
        UserModel userInfo = userRepository.findByUserId(id);
        return userInfo;
    }

    public String getAllUserRideStatus(){
        List<UserModel> usersList = getUsers();
        String rideStatus = "";
        for(int i=0 ; i<usersList.size();i++){
            UserModel user = usersList.get(i);
            String userStatus = user.getName() + ": Taken - " + user.getTaken_count() + " Offer - " + user.getOffered_count() ;
            rideStatus = rideStatus + userStatus + "\n";
        }
        return rideStatus;
    }


}

