package com.example.demo.controller;

import com.example.demo.domain.dto.Esdata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EsController {

    private final RestHighLevelClient restHighLevelClient;


    @GetMapping("/es/index")
    public String createIndex() throws IOException {

        CreateIndexRequest request = new CreateIndexRequest("my-emo");
        request.settings(Settings.builder()
                .put("index.mapping.ignore_malformed", true)
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 1)
        );
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "tcc");
        jsonMap.put("decription", "tcc");
        request.source(jsonMap);
        //request.mapping(prop, XContentType.JSON);

        restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return "SUCCESS";
    }


    @GetMapping("/es/insert")
    public String insertData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("my-emo");
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
        jsonMap.put("name", "tcc");
        jsonMap.put("decription", "tcc");
        indexRequest.id(UUID.randomUUID().toString().replace("-", "")).source(
                new Esdata(UUID.randomUUID().toString().replace("-", ""),"myyy","sdfsdfs"),
                XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return "SUCCESS";
    }


    @PostMapping("/es/del")
    public String delIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("my-emo");
        restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        return "SUCCESS";
    }


    @GetMapping("/es/query")
    public String queryData() {
        String result = "";


        return result;

    }
}
