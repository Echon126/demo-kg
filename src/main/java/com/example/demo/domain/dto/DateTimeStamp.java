package com.example.demo.domain.dto;

import com.example.demo.entity.Notes;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DateTimeStamp {
    private Timestamp startTime;
    private String name;
    private Notes notes;
}
