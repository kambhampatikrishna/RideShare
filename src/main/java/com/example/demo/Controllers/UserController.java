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

       try{
           UserModel userInfo = userService.getUserById(userId);
           if(userInfo == null) {
               return "user not found";
           }
           else {
               return userInfo.toString();
           }
       }
       catch (Exception e){
            e.printStackTrace();
       }

       return "not found";

    }


//    @GetMapping(path = "/addUser")
//    public ModelAndView userForm(Model model){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("UserForm");
//        model.addAttribute("user", new UserModel());
//        return modelAndView;
//    }

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
    public UserModel updateUserInfo(@RequestBody UserModel userModel, @PathVariable("userId") Long userId){
        UserModel userInfo = getUserById(userId);
        userInfo.setName(userModel.getName());
        userInfo.setAge(userModel.getAge());
        userInfo.setOffered_count(userModel.getOffered_count());
        userInfo.setTaken_count(userModel.getTaken_count());
        userInfo.setGender(userModel.getGender());
        userService.addUser(userInfo);
        return userInfo;

    }



    @GetMapping(path = "/allUserRides")
    public String printRideStatus(){
       String res = userService.getAllUserRideStatus();
       return res;
    }

    public UserModel getUserById(Long id){
        UserModel userInfo = userService.getUserById(id);
        return userInfo;
    }

}
