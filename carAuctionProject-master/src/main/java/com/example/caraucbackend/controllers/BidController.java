package com.example.caraucbackend.controllers;


import com.example.caraucbackend.DTOs.GeneralResponse;
import com.example.caraucbackend.DTOs.Requests.NewBidRequest;
import com.example.caraucbackend.services.BidServices;
import com.example.caraucbackend.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.caraucbackend.DTOs.GeneralResponseBody;

import java.time.LocalDate;
import java.time.LocalTime;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bids")
public class BidController {


    @Autowired
    private BidServices bidServices;
    @Autowired
    private UserServices userServices;


    @PostMapping("/new/{username}/{password}")
    @ResponseBody
    private GeneralResponse addNewBid(@RequestBody NewBidRequest bidRequest,
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
        return bidServices.addNewBid(bidRequest);
    }


    @GetMapping("/find/{username}/{password}")
    @ResponseBody
    private GeneralResponse getBidsByUsername(
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
        return bidServices.getBidsByUsername(username);
    }
}
