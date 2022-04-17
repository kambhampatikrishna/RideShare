package com.example.demo.Service;

import com.example.demo.Exceptions.UserNotFoundException;
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

    public String updateUserInfo(UserModel userModel,Long userId){
        UserModel userInfo = userRepository.findByUserId(userId);
        userInfo.setName(userModel.getName());
        userInfo.setAge(userModel.getAge());
        userInfo.setOffered_count(userModel.getOffered_count());
        userInfo.setTaken_count(userModel.getTaken_count());
        userInfo.setGender(userModel.getGender());
        userRepository.save(userInfo);
        return userInfo.toString();
    }

    public UserModel findUserById(Long id){
        UserModel userInfo = userRepository.findByUserId(id);
        return userInfo;
    }

    public String getUserById(Long id){
        UserModel userInfo = new UserModel();
        try{
            userInfo = userRepository.findByUserId(id);
            if(userInfo == null) {
                throw  new UserNotFoundException("user not found");
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return userInfo.toString();

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

