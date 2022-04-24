package com.example.demo.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangwenjie
 * @since 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Notes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String description;

    public Notes() {

    }

    public Notes(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
