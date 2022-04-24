package com.example.demo;

import com.example.demo.domain.entity.TGroupParam;
import com.example.demo.mapper.TGroupParamMapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedDoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
class DemoApplicationTests {

//	@Test
//	void contextLoads() {
//	}

    @Autowired
    private TGroupParamMapper mapper;

    @Test
    public void test() {
        TGroupParam groupParam = new TGroupParam();
        groupParam.setGroupId("783d8f44594f46928b35e53744fee275");
        groupParam.setParamKey("param_key");
        groupParam.setSort(0);
        mapper.insert(groupParam);
    }

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Test
    public void tests() {
        try {
//            Context context = new Context();
//            //设置参数
//            context.setVariable("id", "123456");
//            //emailTemplate为模板文件的文件名，即html demo的文件名
//            Map<String, Object> root = new HashMap<String, Object>();
//            root.put("weatherInfo", "xxx");
//            Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("message.tl");
//            String retSmsContent = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);

            FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
            configurer.setDefaultEncoding("UTF-8");
            configurer.setTemplateLoaderPath("classpath:/message.tl");
            Map<String, Object> variables = new HashMap<>(1<<1);
            variables.put("xml_escape","fmXmlEscape");
            configurer.setFreemarkerVariables(variables);

            Locale locale = new Locale("zh");
            //Template template = configurer.getTemplate("文件名.ftl",locale,"UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }


        //mailService.sendHtmlMail("595726017@qq.com", "helloWorld", tempContext);
    }


    private RestHighLevelClient restHighLevelClient;

    @Autowired
    public DemoApplicationTests(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     *基于terms类型进行聚合  基于字段类型进行分组聚合
     */
    @Test
    public void testAggs() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .query(QueryBuilders.matchAllQuery())//查询条件
                .aggregation(AggregationBuilders.terms("price_group").field("price"));//用来设置聚合处理

        SearchRequest searchRequest = new SearchRequest("products");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //处理聚合结果
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedDoubleTerms parsedDoubleTerms = aggregations.get("price_group");//根据处理字段的类型来选择ParsedXXXTerms

        List<? extends Terms.Bucket> buckets = parsedDoubleTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println("价格：" + bucket.getKey() + "---" + "文档数量：" + bucket.getDocCount());
        }

    }

    @Test
    public    void creatIndex(String json){
//        IndexRequest indexRequest = new IndexRequest("test_index","String");
//        indexRequest.source(json, XContentType.JSON);//这里的json为存入ES的文档内容
//
        IndexRequest indexRequest = new IndexRequest("index","type","id")
                .source("name", "tcc",
                        "decription", "tcc");

        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);//RequestOptions在新办法中必须抉择
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
