package com.example.demo.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExcelModel {
    private String subDeviceKey;
    private String location;
    private String simpleInterval;
    private List<ExcelSubModel> subModelList;
}
