package com.example.caraucbackend.controllers;

import com.example.caraucbackend.DTOs.GeneralResponse;
import com.example.caraucbackend.DTOs.GeneralResponseBody;
import com.example.caraucbackend.entities.User;
import com.example.caraucbackend.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;


@CrossOrigin(origins = "*")
@RestController
public class UserController {



    @Autowired
    private UserServices userServices;



    @GetMapping("/login/{username}/{password}")
    @ResponseBody
    private GeneralResponse userLoginTo(@PathVariable String username, @PathVariable String password){
        return userServices.login(username, password);
    }


    @PostMapping("/add")
    @ResponseBody
    private GeneralResponse addNewUser(@RequestBody User user){
        return userServices.addNewUser(user);
    }


    @PostMapping("/block/{userId}/{username}/{password}")
    @ResponseBody
    private GeneralResponse addCar(@PathVariable String userId,
                                   @PathVariable String username,
                                   @PathVariable String password){
        if(!userServices.usernameAndPasswordChecker(username, password)){
            return new GeneralResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Wrong Security Credentials",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody(null)
            );
        }
        return userServices.blockUser(userId);
    }

}
