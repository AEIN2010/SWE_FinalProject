package com.example.caraucbackend.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@Getter
@Setter
@Data
@JsonSerialize
public class GeneralResponse {

    @NotNull
    private HttpStatus httpStatus;
    @NotNull
    private String Message;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;
    @NotNull
    private GeneralResponseBody body;

}
