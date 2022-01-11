package com.example.demo.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.kafka.Contants;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.kafka.KafkaSender;
import com.example.demo.kafka.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisController {

    @GetMapping("/batch/redis")
    public String batchSendKafka(int val) {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        RedisDelayingQueue<String> queue = new RedisDelayingQueue<>(jedis, "q-demo");
        queue.delay("codehole" + val, 5000 * val);

        return "SUCCESS";
    }


    public static void main(String[] args) {
        try {
            //TODO 通过URL 获取minio文件信息
            InputStream in = new URL("https://test1126.cogiot.net/tieta/%E8%B5%B7%E5%BA%95JVM%E5%86%85%E5%AD%98%E7%AE%A1%E7%90%86%E5%8F%8A%E6%80%A7%E8%83%BD%E8%B0%83%E4%BC%9820210709%40%E6%8A%80%E6%9C%AF%E5%85%AB%E7%82%B9%E5%8D%8A%20%281%29.pdf?Content-Disposition=attachment%3B%20filename%3D%22%E8%B5%B7%E5%BA%95JVM%E5%86%85%E5%AD%98%E7%AE%A1%E7%90%86%E5%8F%8A%E6%80%A7%E8%83%BD%E8%B0%83%E4%BC%9820210709%40%E6%8A%80%E6%9C%AF%E5%85%AB%E7%82%B9%E5%8D%8A%20%281%29.pdf%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=Admin_2021%40zlsj%2F20211217%2F%2Fs3%2Faws4_request&X-Amz-Date=20211217T064625Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=6b8619782f6270a7389935466977883b47430f41a7055cee2f2fe199ee625030").openStream();

            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(inR);

//            String line;
//            while ((line = buf.readLine()) != null) {
//                System.out.println(line);
//            }

            File file = new File("/Users/zhangwenjie/excel/test.pdf");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] b = new byte[1024];
            int length;
            while ((length = in.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
