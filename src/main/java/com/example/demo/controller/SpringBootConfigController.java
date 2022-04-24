package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.APIResultBody;
import com.example.demo.config.ApplicationConfigure;
import com.example.demo.design.TestDesignService;
import com.example.demo.entity.Notes;
import com.example.demo.utils.CogiotBaseRsp;
import com.example.demo.utils.RestTemplateUtil;
import com.example.demo.utils.RestTemplateUtils;
import com.fasterxml.jackson.core.TreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.tea.*;
import com.aliyun.actiontrail20200706.*;
import com.aliyun.actiontrail20200706.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;

@Slf4j
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

    @ApiOperation(value = "callUrl", notes = "第三方回调接口")
    @PostMapping("/callUrl")
    public APIResultBody callUrl(@RequestBody String jsonStr) {
        log.info("事件回调接口,result is {}", jsonStr);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "success");
        map.put("code", 200);
        map.put("data", jsonStr);
        return APIResultBody.success();
    }

    @Autowired
    private TestDesignService testDesignService;

    @GetMapping("/stratgy")
    public String getInfo(String StrategyInterfaceMehod001) {
        return testDesignService.strategyMethod(StrategyInterfaceMehod001);
    }


    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.actiontrail20200706.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "actiontrail.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.actiontrail20200706.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.actiontrail20200706.Client client = SpringBootConfigController.createClient("accessKeyId", "accessKeySecret");
        CreateDeliveryHistoryJobRequest createDeliveryHistoryJobRequest = new CreateDeliveryHistoryJobRequest()
                .setTrailName("test")
                .setClientToken("test");
        // 复制代码运行请自行打印 API 的返回值
        client.createDeliveryHistoryJob(createDeliveryHistoryJobRequest);
    }

    @PostMapping("/getController")
    public CogiotBaseRsp<List<Notes>> getController() {
        List<Notes> notesList = new ArrayList<Notes>() {{
            add(new Notes(1, "xx", "1"));
            add(new Notes(2, "ss", "1"));
            add(new Notes(3, "ss", "1"));
            add(new Notes(4, "ss", "1"));
        }};

        return CogiotBaseRsp.success(notesList);
    }

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @GetMapping("/getJson")
    public CogiotBaseRsp<List<Notes>> getControllerInfo() {
        JSONObject jsonObject = new JSONObject();
//        restTemplateUtils.postExchangeAsList("http://localhost:8089/app/getController", "", new ParameterizedTypeReference<List<String>>() {
//        }));

        String body = "hello world";
        String url = "http://localhost:8089/app/getController";
//        List<Notes> notes = restTemplateUtils.postExchangeAsList("http://localhost:8089/app/getController", new String(), new ParameterizedTypeReference<List<Notes>>() {
//        });

        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", "orderCode");

        //https://stackoverflow.com/questions/36915823/spring-resttemplate-and-generic-types-parameterizedtypereference-collections-lik
        //https://www.iteye.com/blog/kanpiaoxue-2513850
        CogiotBaseRsp<List<Notes>> result = RestTemplateUtil.postForList(url, params, new ParameterizedTypeReference<CogiotBaseRsp<List<Notes>>>() {
        });


        return result;
    }
}
