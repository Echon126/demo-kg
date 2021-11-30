package com.example.demo.utils;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
@Builder(toBuilder = true)
public class MailContentModel implements Serializable {
    private static final long serialVersionUID = -3076705429120033801L;

    private String messageCode;
    private String messageStatus;
    private String cause;
}
