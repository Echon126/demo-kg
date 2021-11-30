package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangwenjie
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Applicationyaml implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    private String email;

    private String application;

    @TableField("fileName")
    private String filename;

    private String description;

    @TableField("creationDate")
    private LocalDateTime creationdate;

    private LocalDateTime updatedate;

    private String yaml;


}
