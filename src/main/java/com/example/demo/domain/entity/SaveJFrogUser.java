package com.example.demo.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangwenjie
 */
@Data
public class SaveJFrogUser implements Serializable {
    private static final long serialVersionUID = 7510770276089348585L;

    private boolean profileUpdatable;
    private boolean disableUIAccess;
    private boolean internalPasswordDisabled;
    private boolean watchManager;
    private boolean reportsManager;
    private boolean policyManager;
    private String name;
    private String email;
    private String password;
    private String retypePassword;
    private List<JFrogUserGroups> userGroups;
}
