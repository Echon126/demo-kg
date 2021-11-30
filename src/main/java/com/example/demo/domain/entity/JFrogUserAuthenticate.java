package com.example.demo.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangwenjie
 */
@Data
public class JFrogUserAuthenticate implements Serializable {

    private static final long serialVersionUID = 3278991935175097769L;

    private String userName;
    private String email;
    private String password;

}
