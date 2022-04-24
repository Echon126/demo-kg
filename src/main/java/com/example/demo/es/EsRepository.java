//package com.example.demo.es;
//
//
//import cn.hutool.core.lang.Snowflake;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.support.WriteRequest;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.springframework.stereotype.Repository;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Repository
//@Slf4j
//public class EsRepository {
//
//    @Resource
//    private RestHighLevelClient highLevelClient;
//
//    /**
//     * 判断索引是否存在
//     */
//    public boolean existIndex(String index) {
//        try {
//            return highLevelClient.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            log.error("es持久层异常！index={}", index, e);
//        }
//        return Boolean.FALSE;
//    }
//
//    /**
//     * 创建索引
//     */
//    public boolean createIndex(String index, Map<String, Object> columnMap) {
//        if (existIndex(index)) {
//            return Boolean.FALSE;
//        }
//
//        CreateIndexRequest request = new CreateIndexRequest(index);
//        if (columnMap != null && columnMap.size() > 0) {
//            Map<String, Object> source = new HashMap<>();
//            source.put("properties", columnMap);
//            request.mapping(source);
//        }
//        try {
//            highLevelClient.indices().create(request, RequestOptions.DEFAULT);
//            return Boolean.TRUE;
//        } catch (IOException e) {
//            log.error("es持久层异常！index={}, columnMap={}", index, columnMap, e);
//        }
//        return Boolean.FALSE;
//    }
//
//    /**
//     * 删除索引
//     */
//    public boolean deleteIndex(String index) {
//        try {
//            if (existIndex(index)) {
//                AcknowledgedResponse response = highLevelClient.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
//                return response.isAcknowledged();
//            }
//        } catch (Exception e) {
//            log.error("es持久层异常！index={}", index, e);
//        }
//        return Boolean.FALSE;
//    }
//
//    /**
//     * 数据新增
//     */
//    public boolean insert(String index, String jsonString) {
//        IndexRequest indexRequest = new IndexRequest(index);
//
//      //  indexRequest.id(new Snowflake().nextIdStr());
//        indexRequest.source(jsonString, XContentType.JSON);
//
//        try {
//            log.info("indexRequest={}", indexRequest);
//            IndexResponse indexResponse = highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
//            log.info("indexResponse={}", indexResponse);
//            return Boolean.TRUE;
//        } catch (IOException e) {
//            log.error("es持久层异常！index={}, jsonString={}", index, jsonString, e);
//        }
//        return Boolean.FALSE;
//    }
//
//    /**
//     * 数据更新，可以直接修改索引结构
//     */
//    public boolean update(String index, Map<String, Object> dataMap) {
//        UpdateRequest updateRequest = new UpdateRequest(index, dataMap.remove("id").toString());
//        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
//        updateRequest.doc(dataMap);
//        try {
//            highLevelClient.update(updateRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            log.error("es持久层异常！index={}, dataMap={}", index, dataMap, e);
//            return Boolean.FALSE;
//        }
//        return Boolean.TRUE;
//    }
//
//    /**
//     * 删除数据
//     */
//    public boolean delete(String index, String id) {
//        DeleteRequest deleteRequest = new DeleteRequest(index, id);
//        try {
//            highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            log.error("es持久层异常！index={}, id={}", index, id, e);
//            return Boolean.FALSE;
//        }
//        return Boolean.TRUE;
//    }
//
//}