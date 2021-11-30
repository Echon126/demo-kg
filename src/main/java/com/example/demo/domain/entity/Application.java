package com.example.demo.domain.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangwenjie
 * @since 2021-06-25
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    @NotBlank(message = "应用名称不能为空")
    @Size(max = 1, message = "应用名称长度过长")
    private String application;

    @Size(max = 1, message = "备注信息长度过长")
    private String description;

    private String status;

    private Date creationdate;

    private Date updatedate;


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        log.info("系统默认编码:{}", System.getProperty("file.encoding"));
        System.setProperty("file.encoding", "GBK");
        log.info("修改后的编码方式:{}", Charset.defaultCharset());
        System.out.println("System.properties: " + System.getProperties());
        System.setProperty("file.encoding", "UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null, null);
    }
}
