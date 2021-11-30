package com.example.demo.domain.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangwenjie
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TParamGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * the device param group name
     */
    private String name;

    /**
     * the device type key
     */
    private String deviceTypeKey;

    /**
     * the group owner account
     */
    private String ownerAccount;

    /**
     * the device param group sort
     */
    private Integer groupSort;


}
