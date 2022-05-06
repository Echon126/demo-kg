package com.example.emo.elastic.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ElasticCommonController {

    private static RestHighLevelClient restHighLevelClients;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @PostConstruct
    public void init() {
        restHighLevelClients = restHighLevelClient;
    }

    public static Map<String, Object> deviceAlarmTemplate() {
        Map<String, Object> messageBody = new HashMap<>();
        //固定值
        messageBody.put("categoryCode", "DEVICE_ALARM");
        messageBody.put("orgId", "111111");
        messageBody.put("title", "DEVICE_OFFLINE");
        messageBody.put("deviceKey", "deviceKey");
        messageBody.put("deviceName", "deviceName");
        messageBody.put("alarm", "createTime");
        messageBody.put("alarmVal", "中国南京");
        messageBody.put("location", "中国南京");
        messageBody.put("phone", "110");
        messageBody.put("principal", "赵四");
        messageBody.put("createTime", "createTime");
        messageBody.put("ammount", 100);
        return messageBody;
    }

    private static BulkProcessor createBulkProcessor() {

        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                log.info("1. 【beforeBulk】批次[{}] 携带 {} 请求数量", executionId, request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  BulkResponse response) {
                if (!response.hasFailures()) {
                    log.info("2. 【afterBulk-成功】批量 [{}] 完成在 {} ms", executionId, response.getTook().getMillis());
                } else {
                    BulkItemResponse[] items = response.getItems();
                    for (BulkItemResponse item : items) {
                        if (item.isFailed()) {
                            log.info("2. 【afterBulk-失败】批量 [{}] 出现异常的原因 : {}", executionId, item.getFailureMessage());
                            break;
                        }
                    }
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  Throwable failure) {

                List<DocWriteRequest<?>> requests = request.requests();
                List<String> esIds = requests.stream().map(DocWriteRequest::id).collect(Collectors.toList());
                log.error("3. 【afterBulk-failure失败】es执行bluk失败,失败的esId为：{}", esIds, failure);
            }
        };

        BulkProcessor.Builder builder = BulkProcessor.builder(((bulkRequest, bulkResponseActionListener) -> {
            restHighLevelClients.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener);
        }), listener);
        //到达10000条时刷新
        builder.setBulkActions(10000);
        //内存到达8M时刷新
        builder.setBulkSize(new ByteSizeValue(8L, ByteSizeUnit.MB));
        //设置的刷新间隔10s
        builder.setFlushInterval(TimeValue.timeValueSeconds(10));
        //设置允许执行的并发请求数。
        builder.setConcurrentRequests(8);
        //设置重试策略
        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1), 3));
        return builder.build();
    }


    @PostMapping("/elastic/pages")
    public Map<String, Object> pageQuery() {
        SearchRequest searchRequest = new SearchRequest("my-emo");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.trackTotalHits(true);
        sourceBuilder.query(QueryBuilders.matchAllQuery())
                //起始位置：start=（page-1）*size
                .from(0)
                //每页显示条数
                .size(10);
        searchRequest.source(sourceBuilder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("总条数：" + search.getHits().getTotalHits().value);
            System.out.println("获取文档最大得分：" + search.getHits().getMaxScore());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> resultData = new HashMap<>(2);
        assert search != null;
        SearchHit[] hits = search.getHits().getHits();
        List<JSONObject> resultList = new ArrayList<>();
        for (SearchHit hit : hits) {
            resultList.add(JSONObject.parseObject(hit.getSourceAsString()));
        }

        resultData.put("data", resultList);
        resultData.put("total", search.getHits().getTotalHits().value);
        return resultData;
    }

    @PostMapping("/elastic/batch/insert")
    public String batchInsertData() {
        List<IndexRequest> indexRequests = new ArrayList<>();
        List<Map<String, Object>> demoDtos = new ArrayList<>();
        Map<String, Object> objectMap = deviceAlarmTemplate();
        for (int i = 0; i < 1000; i++) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>(objectMap);
            stringObjectHashMap.put("categoryCode","categoryCode"+(i+"-ABCD"));
            demoDtos.add(stringObjectHashMap);
        }
        int count = 1000;
        demoDtos.forEach(e -> {
            IndexRequest request = new IndexRequest("my-emo");
            //填充id
            String uuid = UUID.randomUUID().toString().replace("-", "");
            request.id(uuid);
            //先不修改id
            e.put("id", uuid);
            //e.put("categoryCode", e.get("categoryCode") + String.valueOf(count));
            request.source(e);
            request.opType(DocWriteRequest.OpType.CREATE);
            indexRequests.add(request);
        });
        indexRequests.forEach(createBulkProcessor()::add);
        return "SUCCESS";
    }


    @PostMapping("/elastic/insert")
    public String insertData() {
        IndexRequest indexRequest = new IndexRequest("my-emo");
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
        jsonMap.put("name", "tcc");
        jsonMap.put("decription", "tcc");
        indexRequest.id(UUID.randomUUID().toString().replace("-", "")).source(deviceAlarmTemplate());
        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }


    @PostMapping("/elastic/delIndex")
    public String delElasticIndex(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        try {
            restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    @PostMapping("/elastic/del")
    public String delElasticIndexData() {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("my-emo");
        try {
            deleteRequest.id("3c8c9c11e9d8474f979dfd841ba7660a");
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    @PostMapping("/elastic/createIndex")
    public String createElasticIndex(String indexName) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                .put("index.mapping.ignore_malformed", true)
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 1)
        );
        request.mapping(" {\n" +
                " \t\"properties\": {\n" +
                "            \"name\":{\n" +
                "             \"type\":\"keyword\"\n" +
                "           },\n" +
                "           \"description\": {\n" +
                "              \"type\": \"text\"\n" +
                "           },\n" +
                "            \"price\":{\n" +
                "             \"type\":\"long\"\n" +
                "           },\n" +
                "           \"pic\":{\n" +
                "             \"type\":\"text\",\n" +
                "             \"index\":false\n" +
                "           }\n" +
                " \t}\n" +
                "}", XContentType.JSON);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "tcc");
        jsonMap.put("decription", "tcc");
        request.source(jsonMap);
        //request.mapping(prop, XContentType.JSON);

        try {
            restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    @PostMapping("/eastic/agg")
    public String elasticAgg() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        /**
         * 使用tag字段进行桶分组
         * 使用sum、avg进行指标聚合
         */
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.
                terms("tag_tr").field("ammount").                   //桶分组
                subAggregation(AggregationBuilders.sum("sum_id").field("ammount")); //求平均值

        searchSourceBuilder.aggregation(aggregationBuilder);
        /**
         * 不输出原始数据
         */
        searchSourceBuilder.size(0);
        /**
         * 打印dsl语句
         */
        log.info("dsl:" + searchSourceBuilder.toString());
        /**
         * 设置索引以及填充语句
         */
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("my-emo");
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("aggs failed.", e);
        }
        /**
         * 解析数据，获取tag_tr的指标聚合参数。
         */
        Aggregations aggregations = response.getAggregations();
        ParsedStringTerms parsedStringTerms = aggregations.get("tag_tr");
        List<? extends Terms.Bucket> buckets = parsedStringTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            //key的数据
            String key = bucket.getKey().toString();
            long docCount = bucket.getDocCount();
            //获取数据
            Aggregations bucketAggregations = bucket.getAggregations();
            ParsedSum sumId = bucketAggregations.get("sum_id");
            ParsedAvg avgId = bucketAggregations.get("avg_id");
            System.out.println(key + ":" + docCount + "-" + sumId.getValue() + "-" + avgId.getValue());
        }

        return "SUCCESS";
    }

    /**
     * 获取count
     *
     * @return
     */
    @PostMapping("/elastic/count")
    public Map<String, Object> elasticCount() {
        Map<String, Object> resultData = new HashMap(2);
        //构建判断条件
        BoolQueryBuilder boolBuilder = new BoolQueryBuilder();

        CountRequest countRequest = new CountRequest("my-emo");
        countRequest.query(boolBuilder);
        CountResponse countResponse;
        long count = 0L;
        try {
            countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            count = countResponse != null ? countResponse.getCount() : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultData.put("count", count);
        resultData.put("message", "SUCCESS");
        return resultData;
    }


    /**
     * 分组统计
     *
     * @return
     */
    @PostMapping("/elastic/term")
    public Map<String, Object> term() {
        Map<String, Object> resultData = new HashMap(2);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //根据姓名进行分组统计个数
        TermsAggregationBuilder field = AggregationBuilders.terms("terms_name").field("categoryCode");
        ValueCountAggregationBuilder countField = AggregationBuilders.count("count_name").field("categoryCode");
        field.subAggregation(countField);
        searchSourceBuilder.aggregation(field);
        SearchRequest searchRequest = new SearchRequest("my-emo").source(searchSourceBuilder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //分组在es中是分桶
        ParsedStringTerms termsName = response.getAggregations().get("terms_name");
        List<? extends Terms.Bucket> buckets = termsName.getBuckets();
        buckets.forEach(naem -> {
            String key = (String) naem.getKey();
            ParsedValueCount countName = naem.getAggregations().get("count_name");
            double value = countName.value();
            log.info("name , count {} {}", key, value);
        });

        return resultData;
    }


//    private BoolQueryBuilder makeQueryParams(Demo demo) {
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//        //精确查找
//        if (demo.getAge() != null) {
//            boolQueryBuilder.must(termQuery("age", String.valueOf(demo.getAge())));
//        }
//        //范围匹配
//        if (!StringUtils.isEmpty(demo.getCreateDate())) {
//            boolQueryBuilder.must(rangeQuery("createDate").gte(demo.getCreateDate()).format("yyyy-MM-dd"));
//        }
//        //模糊匹配
//        if (!StringUtils.isEmpty(demo.getName())) {
//            boolQueryBuilder.must(QueryBuilders.wildcardQuery("name", String.format("*%s*", demo.getName())));
//        }
//        return boolQueryBuilder;
//    }
}
