package com.example.caraucbackend.DTOs;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralResponseBody<T> {
    private T data;

    public GeneralResponseBody(T data) {
        this.data = data;
    }

    public GeneralResponseBody(Void data) {
        this.data = null;
    }

    // Getters and setters for data
}
