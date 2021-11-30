package com.example.demo.controller;

import com.example.demo.config.ApplicationConfigure;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/app")
@Api(tags = "获取 Springboot相关配置文件信息")
public class SpringBootConfigController {

    @Autowired
    private ApplicationConfigure applicationConfigure;

    @RequestMapping("/app")
    public String demoApplicationProperties() {
        return applicationConfigure.getPassword();
    }

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @GetMapping("/getSourceProperties")
    public String getDataSourceProperties() {
        String url = this.dataSourceProperties.getUrl();
        String username = this.dataSourceProperties.getUsername();
        String password = this.dataSourceProperties.getPassword();

        return String.format("%s--%s--%s", url, username, password);
    }

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/servlet")
    public String httpservlet(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = httpServletRequest.getParameter("name");
        return id + name;
    }
}
