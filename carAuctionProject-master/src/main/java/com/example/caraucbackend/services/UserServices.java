package com.example.caraucbackend.services;


import com.example.caraucbackend.DTOs.GeneralResponse;
import com.example.caraucbackend.DTOs.GeneralResponseBody;
import com.example.caraucbackend.repos.UserRepo;
import com.example.caraucbackend.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserRepo userRepo;

    public GeneralResponse login(String username, String password){

        User userFound = userRepo.findUserByUsernameIsAndPasswordIs(username, password);

        if(userFound != null){
            return new GeneralResponse(
                    HttpStatus.FOUND,
                    "User logged in",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userFound)
            );
        }else{
            return new GeneralResponse(
                    HttpStatus.NOT_FOUND,
                    "Wrong username or password",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userFound)
            );
        }

    }

    public boolean usernameAndPasswordChecker(String username, String password){

        User user = userRepo.findUserByUsernameIsAndPasswordIs(username, password);
        if (user != null){
            if (user.getIsBlocked() != 'Y')
                return true;
        }
        return false;

    }



    public boolean userExists(String username){

        return userRepo.findUserByUsernameIs(username) != null;
    }


    public User getUserByUserName(String username){
        return userRepo.findUserByUsernameIs(username);
    }


//    public User updateUser(User user){
//        return userRepo.save(user);
//    }

    public GeneralResponse addNewUser(User user) {
        if(userExists(user.getUsername())){
            return new GeneralResponse(
                    HttpStatus.BAD_REQUEST,
                    "Username taken!",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );
        }else{
            return new GeneralResponse(
                    HttpStatus.ACCEPTED,
                    "User Added!",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userRepo.save(user))
            );
        }
    }


    public GeneralResponse blockUser(String username) {
        User user = getUserByUserName(username);

        if (user == null) {
            return new GeneralResponse(
                    HttpStatus.BAD_REQUEST,
                    "Username doesnot exist!",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );
        } else {
            user.setIsBlocked('Y');
            return new GeneralResponse(
                    HttpStatus.ACCEPTED,
                    "User Added!",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(userRepo.save(user))
            );
        }
    }

}
