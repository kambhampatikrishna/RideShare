package com.example.demo.Controllers;

import com.example.demo.Models.UserModel;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v0/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}")
    public String  getUser( @PathVariable("userId") Long userId){
        String userInfo = userService.getUserById(userId);
        return userInfo;

    }


    @GetMapping(path = "/all")
    public List<UserModel> getAll(){
        List<UserModel>users = userService.getUsers();
        return users;
    }

    @RequestMapping(path = "/addUser", method = RequestMethod.POST)
    public UserModel saveUser(@RequestBody  UserModel user){
        userService.addUser(user);
        return user;
    }

    @PutMapping(path = "/updateUserInfo/{userId}")
    public String updateUserInfo(@RequestBody UserModel userModel, @PathVariable("userId") Long userId){
        String userInfo = userService.updateUserInfo(userModel,userId);
        return userInfo;
    }


    @GetMapping(path = "/allUserRides")
    public String printRideStatus(){
       String res = userService.getAllUserRideStatus();
       return res;
    }


}
