package com.example.demo.rpc;

import com.example.demo.config.ApplicationConfigure;
import com.example.demo.domain.dto.JfrogDeleteUser;
import com.example.demo.domain.entity.SaveJFrogUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwenjie
 */
@Slf4j
@Service
public class JFrogRpcServiceImpl extends AbstractJFrogRpcService implements JFrogRpcSevice {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ApplicationConfigure configure;

    private static final String DEL_USER = "ui/api/v1/ui/users/userDelete?jfLoader=true";
    private static final String SAVE_USER = "ui/api/v1/ui/users?$suppress_toaster=false";


    @Override
    public boolean deleteJfrogUser(JfrogDeleteUser jfrogDeleteUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "application/json, text/plain, */*");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.add(HttpHeaders.COOKIE, this.authenticate());

        Map<String, Object> map = new HashMap<>();
        map.put("userNames", jfrogDeleteUser.getUserNames());
        //添加请求的实体类，这里第一个参数是要发送的参数，第二个参数是请求头里的数据
        HttpEntity<Object> requestEntity = new HttpEntity<>(map, headers);

        ResponseEntity<String> result;
        try {
            log.info("系统默认编码:{}", System.getProperty("file.encoding"));
            result = restTemplate.exchange(this.configure.getJfrogUrl() + DEL_USER, HttpMethod.POST, requestEntity, String.class);
            log.info("delete Jfrog user :{}", result.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            log.error("delete Jfrog user error", e);
        }
        return true;
    }

    /**
     * @param jFrogUser
     * @return
     */
    @Override
    public boolean saveJFrogUser(SaveJFrogUser jFrogUser) {
        /**
         * {
         * "profileUpdatable":true,
         * "disableUIAccess":false,
         * "internalPasswordDisabled":false,
         * "watchManager":false,
         * "reportsManager":false,
         * "policyManager":false,
         * "name":"zhangwenjie5",
         * "email":"1352970608@qq.com",
         * "password":"ABCabc123",
         * "retypePassword":"ABCabc123",
         * "userGroups":[
         * {
         * "groupName":"readers",
         * "realm":"internal"
         * },
         * {
         * "groupName":"zhaoxutest1-group",
         * "realm":"internal"
         * }
         * ]
         * }
         */
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "application/json, text/plain, */*");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.add(HttpHeaders.COOKIE, this.authenticate());

        //添加请求的实体类，这里第一个参数是要发送的参数，第二个参数是请求头里的数据
        HttpEntity<Object> requestEntity = new HttpEntity<>(jFrogUser, headers);

        ResponseEntity<String> result;
        try {
            result = restTemplate.exchange(this.configure.getJfrogUrl() + SAVE_USER, HttpMethod.POST, requestEntity, String.class);
            log.info("save Jfrog user :{}", result.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            log.error("save Jfrog user error", e);
        }
        return false;
    }

    @Override
    public boolean editJFrogUser(SaveJFrogUser jFrogUser) {
        return false;
    }
}
