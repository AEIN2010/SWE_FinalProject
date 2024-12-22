package com.example.caraucbackend.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.NotNull;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    private String name;

    @Id
    private String username;
    @NotNull
    private String password;
    @NotNull
    private char isAdmin;
    @ColumnDefault("N")
    private char isBlocked;



}
