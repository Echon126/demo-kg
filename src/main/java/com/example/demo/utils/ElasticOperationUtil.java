package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * es 公共操作类
 */
@Slf4j
public class ElasticOperationUtil {

    private final RestHighLevelClient client;

    public ElasticOperationUtil(RestHighLevelClient restHighLevelClient) {
        this.client = restHighLevelClient;
    }

    private static final Integer DEFAULT_SHARDS = 1;
    private static final Integer DEFAULT_REPLICAS = 1;

    /**
     * 创建index
     *
     * @param indexName indexName
     * @param prop      properties
     * @throws IOException ex
     */
    public void createIndex(String indexName, String prop, Integer shards, Integer replicas) throws IOException {
        if (verifyExists(indexName)) {
            log.warn("index already exists");
            return;
        }

        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                .put("index.mapping.ignore_malformed", true)
                .put("index.number_of_shards", shards == null ? DEFAULT_SHARDS : shards)
                .put("index.number_of_replicas", replicas == null ? DEFAULT_REPLICAS : replicas)
        );
        request.mapping(prop, XContentType.JSON);

        client.indices().create(request, RequestOptions.DEFAULT);
    }


    /**
     * 删除index
     *
     * @param indexName indexName
     * @throws IOException ex
     */
    public void delIndex(String indexName) throws IOException {
        if (verifyExists(indexName)) {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            client.indices().delete(request, RequestOptions.DEFAULT);
        }
    }


    /**
     * 校验index是否存在
     *
     * @param index indexName
     * @return true/false
     * @throws IOException ex
     */
    public boolean verifyExists(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }


    /**
     * 新增文档
     *
     * @param indexName indexName
     * @param data      data
     */
    public void addDocument(String indexName, Map<String, Object> data) throws IOException {
        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.id(UUID.randomUUID().toString().replace("-", "")).source(data);
        client.index(indexRequest, RequestOptions.DEFAULT);
    }


    /**
     * 批量新增文档
     *
     * @param indexName indexName
     * @param list      dataList
     * @throws IOException ex
     */
    public void bulkAddDocument(String indexName, List<Map<String, Object>> list) throws IOException {
        BulkRequest request = new BulkRequest();
        IndexRequest indexRequest;
        for (Map<String, Object> data : list) {
            indexRequest = new IndexRequest();
            indexRequest.index(indexName).id(UUID.randomUUID().toString().replace("-", "")).source(data);
            request.add(indexRequest);
        }
        client.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量新增文档
     *
     * @param indexName indexName
     * @param list      dataList
     * @throws IOException ex
     */
    public void bulkAddDocument(String indexName, List<Map<String, Object>> list, String idKey) throws IOException {
        BulkRequest request = new BulkRequest();
        IndexRequest indexRequest;
        String id;
        for (Map<String, Object> data : list) {
            id = data.get(idKey) == null ? UUID.randomUUID().toString().replace("-", "") : String.valueOf(data.get(idKey));
            indexRequest = new IndexRequest();
            indexRequest.index(indexName).id(id).source(data);
            request.add(indexRequest);
        }
        client.bulk(request, RequestOptions.DEFAULT);
    }


    /**
     * 根据条件删除
     *
     * @param indexName indexName
     * @param builder   condition
     * @throws IOException ex
     */
    public void deleteByQuery(String indexName, QueryBuilder builder) throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
        request.setQuery(builder);
        client.deleteByQuery(request, RequestOptions.DEFAULT);
    }


    /**
     * get index mappings
     *
     * @param indexName indexName
     * @return mappings
     * @throws IOException ex
     */
    public Map<String, Object> getIndexMapping(String indexName) throws IOException {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(indexName);
        GetMappingsResponse mapping = client.indices().getMapping(request, RequestOptions.DEFAULT);
        return mapping.mappings().get(indexName).getSourceAsMap();
    }


    /**
     * 异步索引 reindex
     *
     * @param target 目标index
     * @param source 源index
     */
    public void reindexAsync(String target, ActionListener<BulkByScrollResponse> listener, String... source) throws IOException {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices(source);
        request.setDestIndex(target);
        client.reindexAsync(request, RequestOptions.DEFAULT, listener);
    }


    /**
     * 同步索引 reindex
     *
     * @param target 目标index
     * @param source 源index
     */
    public void reindexSync(String target, String... source) throws IOException {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices(source);
        request.setSourceQuery(QueryBuilders.matchAllQuery());
        request.setDestIndex(target);
        client.reindex(request, RequestOptions.DEFAULT);
    }


    public long countDocuments(String indexName) throws IOException {
        CountRequest countRequest = new CountRequest(indexName);
        CountResponse count = client.count(countRequest, RequestOptions.DEFAULT);
        return count.getCount();
    }


    public SearchResponse pageRequest(String indexName, int page, int size) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.trackTotalHits(true);
        sourceBuilder.from((page - 1) * size);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indexName);
        searchRequest.source(sourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

}
