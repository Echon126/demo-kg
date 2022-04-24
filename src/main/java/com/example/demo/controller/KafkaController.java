package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.domain.dto.DateTimeStamp;
import com.example.demo.kafka.Contants;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.kafka.KafkaSender;
import com.example.demo.kafka.User;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.cms.TimeStampAndCRL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaController {
    private final KafkaProducer kafkaProducer;
    private final KafkaSender kafkaSender;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/mq")
    public String sendMQ(Integer amount) {
        int count = 0;
        JSONObject jsonObject = JSONObject.parseObject("{\"data\":[{\"creator\":\"wdqy\",\"deviceTypeId\":\"62184799e151b539b6fa2303\",\"originOrgId\":\"64567ef1a8cd41b49f9fffa573dbad32\",\"isConnect\":2,\"origin\":\"telit\",\"deviceKey\":\"testcallback014\",\"updateTime\":\"2022-03-30 06:58:42\",\"deviceName\":\"testcallback014\",\"orgId\":\"fa51fec5bd8e4554a1084a41afc84c86\",\"deviceTypeKey\":\"f8d3d21fbec7421e81f56af1e7f02860\",\"createTime\":\"2022-03-30 06:58:42\",\"displayDeviceKey\":\"testcallback014\",\"parentDeviceId\":\"\",\"location\":\"\",\"id\":\"6243ffa23821d1529dcf7dda\",\"status\":0}],\"deviceKey\":\"62184799e151b539b6fa2303\",\"type\":\"1\"}");
        kafkaProducer.send(jsonObject.toString());

        return "SUCCESS";
    }

    @Value("#{'${spring.kafka.listener.topics}'.split(',')}")
    private List<String> topics;

    @GetMapping("/batch/send")
    public String batchSendKafka() {

        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send("test-0001", "xxxxx");
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("failure");
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info("success");
            }
        });
//        }

        return "SUCCESS";
    }


//    public static void main(String[] args) {
//        try {
//            //TODO 通过URL 获取minio文件信息
//            InputStream in = new URL("https://test1126.cogiot.net/tieta/%E8%B5%B7%E5%BA%95JVM%E5%86%85%E5%AD%98%E7%AE%A1%E7%90%86%E5%8F%8A%E6%80%A7%E8%83%BD%E8%B0%83%E4%BC%9820210709%40%E6%8A%80%E6%9C%AF%E5%85%AB%E7%82%B9%E5%8D%8A%20%281%29.pdf?Content-Disposition=attachment%3B%20filename%3D%22%E8%B5%B7%E5%BA%95JVM%E5%86%85%E5%AD%98%E7%AE%A1%E7%90%86%E5%8F%8A%E6%80%A7%E8%83%BD%E8%B0%83%E4%BC%9820210709%40%E6%8A%80%E6%9C%AF%E5%85%AB%E7%82%B9%E5%8D%8A%20%281%29.pdf%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=Admin_2021%40zlsj%2F20211217%2F%2Fs3%2Faws4_request&X-Amz-Date=20211217T064625Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=6b8619782f6270a7389935466977883b47430f41a7055cee2f2fe199ee625030").openStream();
//
//            InputStreamReader inR = new InputStreamReader(in);
//            BufferedReader buf = new BufferedReader(inR);
//
////            String line;
////            while ((line = buf.readLine()) != null) {
////                System.out.println(line);
////            }
//
//            File file = new File("/Users/zhangwenjie/excel/test.pdf");
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            FileOutputStream outputStream = new FileOutputStream(file);
//
//            byte[] b = new byte[1024];
//            int length;
//            while ((length = in.read(b)) > 0) {
//                outputStream.write(b, 0, length);
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }


    @PostMapping("/post")
    public String PostRequest(@RequestBody DateTimeStamp startTime) {
        log.info("" + startTime);
        return "SUCCESS";
    }

    @GetMapping("/get")
    public String GetRequest(DateTimeStamp startTime) {
        log.info("" + startTime);
        return "SUCCESS";
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        HttpRequest post = HttpRequest.post("http://localhost:8089/send");
        String body = post.body();
    }
}
