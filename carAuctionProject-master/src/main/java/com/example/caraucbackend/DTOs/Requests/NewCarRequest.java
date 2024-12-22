package com.example.caraucbackend.DTOs.Requests;


import com.example.caraucbackend.entities.CarStatus;
import lombok.*;

import javax.validation.constraints.NotNull;


@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class NewCarRequest {

    @NotNull
    private String vin;
    @NotNull
    private String make;
    @NotNull
    private String model;
    @NotNull
    private String year;
    @NotNull
    private long price;
    @NotNull
    private String image;
    @NotNull
    private String mileage;
    @NotNull
    private CarStatus carStatus;
    @NotNull
    private String listerUsername;


}
