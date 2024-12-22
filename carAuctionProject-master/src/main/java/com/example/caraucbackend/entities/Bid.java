package com.example.caraucbackend.entities;


import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Bid {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bidID;
    @NotNull
    private double amount;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;

    @ManyToOne
    @NotNull
    private User bidder;


    public Bid(double amount, LocalDate date, LocalTime time, User bidder) {
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.bidder = bidder;
    }
}
