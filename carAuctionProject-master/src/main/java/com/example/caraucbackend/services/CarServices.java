package com.example.caraucbackend.services;


import com.example.caraucbackend.DTOs.GeneralResponse;
import com.example.caraucbackend.DTOs.Requests.NewCarRequest;
import com.example.caraucbackend.DTOs.GeneralResponseBody;
import com.example.caraucbackend.entities.CarStatus;
import com.example.caraucbackend.entities.User;
import com.example.caraucbackend.repos.CarRepo;
import com.example.caraucbackend.entities.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServices {

    private final CarRepo carRepo;
    private final UserServices userServices;

    public GeneralResponse getAllCars(){
        List<Car> cars = carRepo.findAll();
        for(Car car:cars){
            car.setBidHistory(null);
        }
        return new GeneralResponse(
                HttpStatus.ACCEPTED,
                "car found",
                LocalDate.now(),
                LocalTime.now(),
                new GeneralResponseBody<>(cars)
        );
    }

    public GeneralResponse getCarByVin(String id){
        Car car = carRepo.findCarByVinIs(id);
        return new GeneralResponse(
                HttpStatus.ACCEPTED,
                "car found",
                LocalDate.now(),
                LocalTime.now(),
                new GeneralResponseBody<>(car)
        );
    }

//    public GeneralResponse getCarsByMake(String make){
//        List<Car> cars = carRepo.findCarsByMakeIs(make);
//
//        for(Car car:cars){
//            car.getLister().setCarList(null);
//            car.getLister().setBidsList(null);
//        }
//
//        return new GeneralResponse(
//                HttpStatus.ACCEPTED,
//                "car found",
//                LocalDate.now(),
//                LocalTime.now(),
//                cars
//        );
//    }

    public GeneralResponse addCar(NewCarRequest car){
        User user = userServices.getUserByUserName(car.getListerUsername());
        if(carRepo.findCarByVinIs(car.getVin()) != null){
            return new GeneralResponse(
                    HttpStatus.NOT_ACCEPTABLE,
                    "car with this VIN already exists!",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );
        }

        carRepo.save(new Car(
                car.getVin(),
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getPrice(),
                car.getImage(),
                car.getMileage(),
                CarStatus.ACTIVE,
                user,
                new ArrayList<>()
        ));

        return new GeneralResponse(
                HttpStatus.ACCEPTED,
                "car added successfully",
                LocalDate.now(),
                LocalTime.now(),
                new GeneralResponseBody<>(car)
        );
    }



    public GeneralResponse markCarAsSold(String vin, String username){

        Car car = carRepo.findCarByVinIs(vin);

        if(car != null && car.getLister().getUsername().equals(username)){

            car.setCarStatus(CarStatus.SOLD);

            return new GeneralResponse(
                    HttpStatus.ACCEPTED,
                    "car marked as sold successfully",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(carRepo.save(car))
            );
        }
        else {
            return new GeneralResponse(
                    HttpStatus.NOT_ACCEPTABLE,
                    "car does not exist",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );        }

    }


    public GeneralResponse markCarAsInactive(String vin, String username){

        Car car = carRepo.findCarByVinIs(vin);

        if(car != null && car.getLister().getUsername().equals(username)){

            car.setCarStatus(CarStatus.INACTIVE);

            return new GeneralResponse(
                    HttpStatus.ACCEPTED,
                    "car marked as sold successfully",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(carRepo.save(car))
            );
        }
        else {
            return new GeneralResponse(
                    HttpStatus.NOT_ACCEPTABLE,
                    "car does not exist",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );        }

    }


    public GeneralResponse deleteACar(String vin, String username){

        Car car = carRepo.findCarByVinIs(vin);

        if(car != null && car.getLister().getUsername().equals(username)){

            car.setCarStatus(CarStatus.SOLD);

            return new GeneralResponse(
                    HttpStatus.ACCEPTED,
                    "car marked as sold successfully",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(carRepo.deleteCarByVinIs(car.getVin()))
            );
        }
        else {
            return new GeneralResponse(
                    HttpStatus.NOT_ACCEPTABLE,
                    "car does not exist",
                    LocalDate.now(),
                    LocalTime.now(),
                    new GeneralResponseBody<>(null)
            );        }

    }



    public GeneralResponse carsListedByUser(String username){

        List<Car> cars = carRepo.findAllByListerUsernameIs(username);

        cars.forEach(car -> {car.setBidHistory(null);
                             car.setLister(null);
                            });

        return new GeneralResponse(
                HttpStatus.FOUND,
                "Cars found for user: "+username,
                LocalDate.now(),
                LocalTime.now(),
                new GeneralResponseBody<>(cars)
        );
    }


    public GeneralResponse updateCar(Car car){
        return new GeneralResponse(
                HttpStatus.ACCEPTED,
                "car updated successfully",
                LocalDate.now(),
                LocalTime.now(),
                new GeneralResponseBody(carRepo.save(car))
        );
    }



}
