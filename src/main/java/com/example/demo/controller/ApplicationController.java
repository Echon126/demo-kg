/*
package com.example.demo.controller;


import com.example.demo.domain.dto.JfrogDeleteUser;
import com.example.demo.domain.entity.SaveJFrogUser;
import com.example.demo.mapper.ApplicationMapper;
import com.example.demo.rpc.JFrogRpcServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

*/
/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangwenjie
 * @since 2021-06-25
 *//*

@Slf4j
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private JFrogRpcServiceImpl jfrogRpcService;
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @GetMapping("/login")
    public String loginJfrog() {
        return "SUCCESS";
    }

    @Value("${spring.mqtt.client.url}")
    private String mqtt;


    @DeleteMapping("/jfrog/user")
    public boolean deleteJfrogUser(@RequestBody JfrogDeleteUser jfrogDeleteUser) {
        return this.jfrogRpcService.deleteJfrogUser(jfrogDeleteUser);
    }

    @PostMapping("/jfrog/user")
    public boolean saveJfrogUser(@RequestBody SaveJFrogUser jFrogUser) {
        return this.jfrogRpcService.saveJFrogUser(jFrogUser);
    }

    @GetMapping("/source")
    public String getDataSource() {
        log.info("MQTT==========:{}", this.mqtt);
        log.info("datasource url:{},username:{},password:{}",
                dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword());
        return this.dataSourceProperties.getUrl();
    }

    @Autowired
    private ApplicationMapper applicationMapper;

    @GetMapping("/v")
    public List<Map<String, Object>> getVehicleInfo() {
        return applicationMapper.getVehicle();
    }

    //@Autowired
   // private DataSourceFactory dataSourceFactory;
    @Autowired
    private SqlSession sqlSession;
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @PostMapping("/sql/import")
    @ApiOperation(value = "导入sql文件")
    public String importVehicleInfo(@RequestParam("file") MultipartFile file) {
        try {

            SqlSession sqlSession = sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
            InputStream inputStream = file.getInputStream();
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            ScriptUtils.executeSqlScript(connection, inputStreamResource);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("execute sql errot", e);
        }
        return "SUCCESS";
    }
}
*/
