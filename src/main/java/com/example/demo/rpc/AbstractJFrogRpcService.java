package com.example.demo.rpc;

import com.example.demo.config.ApplicationConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwenjie
 */
public abstract class AbstractJFrogRpcService {
    private static final String J_FROG_URL = "ui/api/v1/ui/auth/login?_spring_security_remember_me=true";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ApplicationConfigure configure;


    /**
     * 功能描述：鉴权获取cookie
     */
    protected String authenticate() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "application/json, text/plain, */*");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        headers.add("X-Requested-With", "XMLHttpRequest");

        Map<String, String> map = new HashMap<>(3);
        map.put("user", this.configure.getUserName());
        map.put("password", this.configure.getPassword());
        map.put("type", this.configure.getType());

        //添加请求的实体类，这里第一个参数是要发送的参数，第二个参数是请求头里的数据
        HttpEntity<Object> requestEntity = new HttpEntity<>(map, headers);

        ResponseEntity<String> exchange = restTemplate.exchange(this.configure.getJfrogUrl() + J_FROG_URL, HttpMethod.POST, requestEntity, String.class);
        List<String> cookieList = exchange.getHeaders().get("SET-COOKIE");
        if (CollectionUtils.isEmpty(cookieList)) {
            throw new RuntimeException("cookie is not empty");
        }

        return MessageFormat.format("{0};{1}", cookieList.get(1), cookieList.get(2));
    }
}
