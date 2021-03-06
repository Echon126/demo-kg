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
//            //????????????
//            context.setVariable("id", "123456");
//            //emailTemplate?????????????????????????????????html demo????????????
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
            //Template template = configurer.getTemplate("?????????.ftl",locale,"UTF-8");

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
     *??????terms??????????????????  ????????????????????????????????????
     */
    @Test
    public void testAggs() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder
                .query(QueryBuilders.matchAllQuery())//????????????
                .aggregation(AggregationBuilders.terms("price_group").field("price"));//????????????????????????

        SearchRequest searchRequest = new SearchRequest("products");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //??????????????????
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedDoubleTerms parsedDoubleTerms = aggregations.get("price_group");//????????????????????????????????????ParsedXXXTerms

        List<? extends Terms.Bucket> buckets = parsedDoubleTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println("?????????" + bucket.getKey() + "---" + "???????????????" + bucket.getDocCount());
        }

    }

    @Test
    public    void creatIndex(String json){
//        IndexRequest indexRequest = new IndexRequest("test_index","String");
//        indexRequest.source(json, XContentType.JSON);//?????????json?????????ES???????????????
//
        IndexRequest indexRequest = new IndexRequest("index","type","id")
                .source("name", "tcc",
                        "decription", "tcc");

        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);//RequestOptions???????????????????????????
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
