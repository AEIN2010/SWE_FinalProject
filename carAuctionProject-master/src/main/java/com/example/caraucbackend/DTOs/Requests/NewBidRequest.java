package com.example.caraucbackend.DTOs.Requests;

import lombok.*;

import javax.validation.constraints.NotNull;


@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class NewBidRequest {


    @NotNull
    private double amount;
    @NotNull
    private String carVin;
    @NotNull
    private String bidderUserName;
}
