package com.example.demo.utils;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@Lazy(false)
public class RestTemplateUtil {
    @Autowired
    private RestTemplate xRestTemplate;
    private static RestTemplate restTemplate;

    public static <T,E> CogiotBaseRsp<List<T>> exchangeAsList(HttpMethod method, String url, E params, ParameterizedTypeReference<CogiotBaseRsp<List<T>>> responseType) {
        HttpEntity requestEntity = new HttpEntity(params);
        return restTemplate.exchange(url, method, requestEntity, responseType).getBody();
    }

    public static <T,E> CogiotBaseRsp<List<T>> postForList(String url,E params, ParameterizedTypeReference<CogiotBaseRsp<List<T>>> responseType) {
        return exchangeAsList(HttpMethod.POST, url, params, responseType);
    }

    @PostConstruct
    public void init() {
        restTemplate = this.xRestTemplate;
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public static void setRestTemplate(RestTemplate restTemplate) {
        RestTemplateUtil.restTemplate = restTemplate;
    }

    public RestTemplate getxRestTemplate() {
        return xRestTemplate;
    }

    public void setxRestTemplate(RestTemplate xRestTemplate) {
        this.xRestTemplate = xRestTemplate;
    }
}

