package com.example.demo.domain.dto;

import lombok.Data;

@Data
public class Esdata {

    private String id;
    private String name;
    private String decription;

    public Esdata(String id, String name, String decription) {
        this.id = id;
        this.name = name;
        this.decription = decription;
    }
}
