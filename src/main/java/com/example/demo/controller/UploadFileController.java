package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.MinioProp;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/file")
@Api(tags = "文件上传")
public class UploadFileController {

    @Autowired
    private MinioClient minioClient;

    @ApiOperation(value = "新增修改货物信息")
    @PostMapping(value = "/x", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> uploadFile(String alarmId, HttpServletRequest request) {
        long s = System.currentTimeMillis();
        Map<String, Object> data = new HashMap<>();
        List<String> result = new ArrayList<String>();
        //多个文件上传  就只是简单的多文件上传保存在本地的磁盘
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
            //<input type="file" name="photo"/>
            try {
                List<MultipartFile> files = mrequest.getFiles("photo");
                for (MultipartFile m : files) {
                    InputStream in = m.getInputStream();
                    String contentType = m.getContentType();
                    String originalFilename = m.getOriginalFilename();
                    PutObjectOptions putObjectOptions = new PutObjectOptions(m.getSize(), PutObjectOptions.MIN_MULTIPART_SIZE);

                    minioClient.putObject(MinioProp.MINIO_BUCKET, originalFilename, in, putObjectOptions);
                    String objectUrl = minioClient.getObjectUrl(MinioProp.MINIO_BUCKET, originalFilename);
                    data.put("bucketName", MinioProp.MINIO_BUCKET);
                    data.put("fileName", objectUrl);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("耗时:{}", (System.currentTimeMillis() - s));
        return data;
    }

    public static void main(String[] args) {
        List<String> s = new ArrayList<>();
        s.add("sdsf");
        s.add("sdfsdf");
        JSONArray objects = JSONObject.parseArray(JSON.toJSONString(s));
        System.out.println(objects.get(0));
    }


    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    @ResponseBody
    public Map<String, Object> xxx(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        long s = System.currentTimeMillis();
        Map<String, Object> data = new HashMap<>();

        InputStream inputStream = file.getInputStream();
        long startTime = System.currentTimeMillis();
        PutObjectOptions putObjectOptions = new PutObjectOptions(inputStream.available(), -1);
        minioClient.putObject(MinioProp.MINIO_BUCKET, file.getOriginalFilename(), inputStream, putObjectOptions);
        log.info("上传耗时:{}，文件大小:{}", (System.currentTimeMillis() - startTime) / 1000 + "秒", file.getSize());

        long ss = System.currentTimeMillis();
        String objectUrl = minioClient.getObjectUrl(MinioProp.MINIO_BUCKET, file.getOriginalFilename());
        log.info("获取连接耗时:{}", (System.currentTimeMillis() - ss) / 1000);


        log.info("耗时:{}", (System.currentTimeMillis() - s) / 1000 + "秒");
        return data;
    }


    public void httpClient() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080");

    }

    @Autowired
    private RestTemplate restTemplate;
//
//    @GetMapping("upload/info")
//    public String uploadT() {
//
//        ResponseEntity<OutputStream> forEntity = this.restTemplate.("https://filesdev.cogiot.net/61a5d6f66303955ef29206c9/TeB7EC-4G-GW-CG20211109_To_20211111.bin", OutputStream.class);
//        OutputStream body = forEntity.getBody();
//
//        return "SUCCESS";
//    }


}
