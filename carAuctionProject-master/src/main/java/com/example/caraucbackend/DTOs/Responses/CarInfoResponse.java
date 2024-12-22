package com.example.caraucbackend.DTOs.Responses;



import com.example.caraucbackend.entities.Bid;
import com.example.caraucbackend.entities.User;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CarInfoResponse {

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
    private User lister;

    @OneToMany
    private List<Bid> bidHistory;
}
