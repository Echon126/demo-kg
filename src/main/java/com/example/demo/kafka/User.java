package com.example.demo.kafka;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class User {
    private String id;
    private String userName;
    private String description;
    //@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
