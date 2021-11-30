package com.example.demo.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TGroupParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * the device param key
     */
    private String paramKey;

    /**
     * the device param group id
     */
    private String groupId;

    /**
     * the device param sort
     */
    private Integer sort;


}
