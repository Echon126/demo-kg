package com.example.demo.utils;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
public class RestTemplateUtils {

    @Autowired
    private RestTemplate restTemplate;




    public <T> CogiotBaseRsp<List<T>> cogiotPost(String url, String jsonStr, Class<T> clazz) {
        ParameterizedType typeRef = new ParameterizedTypeImpl(new Type[]{clazz}, CogiotBaseRsp.class.getDeclaringClass(), CogiotBaseRsp.class);
        ResponseEntity<CogiotBaseRsp<List<T>>> exchange = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(""), ParameterizedTypeReference.forType(typeRef));
        return exchange.getBody();
    }


//    public <T> CogiotBaseRsp<List<T>> gg(String url){
//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
//        params.add("pageNo",1);
//        HttpEntity entity = new HttpEntity(params,null);
//        List<T> testList = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<T>>() {}).getBody();
//
//        return testList;
//    }


    public <T> List<T> exchangeAsList(String uri, ParameterizedTypeReference<List<T>> responseType) {
        return restTemplate.exchange(uri, HttpMethod.POST, null, responseType).getBody();
    }


    public <T, E> List<T> postExchangeAsList(String url, E body, ParameterizedTypeReference<List<T>> responseType) {
        HttpHeaders headers = buildHeader();
        HttpEntity<E> entity = new HttpEntity<E>(body, headers);
        HttpEntity<List<T>> httpResponse =
                restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        List<T> result = httpResponse.getBody();
        return result;
    }

    private HttpHeaders buildHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("authToken", "hello-token");
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }




}
