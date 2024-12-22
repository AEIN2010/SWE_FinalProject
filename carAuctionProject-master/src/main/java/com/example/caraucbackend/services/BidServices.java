package com.example.caraucbackend.services;


import com.example.caraucbackend.DTOs.GeneralResponse;
import com.example.caraucbackend.DTOs.Requests.NewBidRequest;
import com.example.caraucbackend.DTOs.GeneralResponseBody;
import com.example.caraucbackend.entities.Bid;
import com.example.caraucbackend.entities.Car;
import com.example.caraucbackend.entities.User;
import com.example.caraucbackend.repos.BidRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class BidServices {

    private final BidRepo bidRepo;
    private final CarServices carServices;
    private final UserServices userServices;

    public GeneralResponse addNewBid(NewBidRequest bidRequest){

        Car car = (Car) carServices.getCarByVin(bidRequest
                        .getCarVin()).getBody().getData();

        if((car != null)
                && userServices.userExists(bidRequest.getBidderUserName())){


            double highestBid = car.getPrice();

            if (bidRequest.getAmount() > highestBid){
                Bid currentBid = bidRepo.save(new Bid(bidRequest.getAmount()
                                        , LocalDate.now()
                                        , LocalTime.now()
                                        , userServices.getUserByUserName(bidRequest.getBidderUserName())));

                //User user = userServices.getUserByUserName(bidRequest.getBidderUserName());
                Car car2 = (Car) carServices.getCarByVin(bidRequest.getCarVin()).getBody().getData();
                car2.getBidHistory().add(currentBid);
                car2.setPrice((long) bidRequest.getAmount());


                //userServices.updateUser(user);
                carServices.updateCar(car2);


                return new GeneralResponse(
                        HttpStatus.ACCEPTED,
                        "Bid Added Successfully",
                        LocalDate.now(),
                        LocalTime.now(),
                        new GeneralResponseBody<>(bidRepo.findById(currentBid.getBidID()))
                );
            }else{
                return new GeneralResponse(
                        HttpStatus.BAD_REQUEST,
                        "Bid Amount Less Than Last Bid!!",
                        LocalDate.now(),
                        LocalTime.now(),
                        new GeneralResponseBody<>(null)
                );
            }
        }else{
            return new GeneralResponse(
                    HttpStatus.BAD_REQUEST,
                    "Car Or User Does Not Exist!!",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );
        }


    }

    public GeneralResponse getBidsByUsername(String username) {
        if(!userServices.userExists(username)){
            return new GeneralResponse(
                    HttpStatus.BAD_REQUEST,
                    "User Did Not Make Any Bids",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );
        }else{
            return new GeneralResponse(
                    HttpStatus.ACCEPTED,
                    "FOUND",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(bidRepo.findAllByBidderUsernameIs(username))
            );
        }
    }

}
