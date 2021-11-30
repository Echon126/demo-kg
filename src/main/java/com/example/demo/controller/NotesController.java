/*
package com.example.demo.controller;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.demo.entity.Notes;
import com.example.demo.mapper.NotesMapper;
import com.example.demo.service.INotesService;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

*/
/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangwenjie
 * @since 2021-08-16
 *//*

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesMapper notesMapper;


    @RequestMapping(value = "/n", method = RequestMethod.GET)
    public List<Notes> getStringNotes() {
        List<Notes> one = new LambdaQueryChainWrapper<>(notesMapper)
                //.eq(Notes::getName, "l")
                .orderByDesc(Notes::getId)
                .list();
        System.out.println("UserOne:" + one);
        return one;
    }

    public static void insert(int num) {
        InfluxDB db = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin");
        // 设置数据库
        db.setDatabase("test_influx");

        // 创建Builder，设置表名
        Point.Builder builder = Point.measurement("test_measurement");
        // 添加Field
        builder.addField("count", num);
        builder.addField("sum", 1);
        // 添加Tag
        //builder.tag("TAG_CODE", "TAG_VALUE_" + num);
        builder.time(System.currentTimeMillis(), TimeUnit.MICROSECONDS);
        Point point = builder.build();
        db.write(point);
    }

    public static void main(String[] args) {
        insert(1);
    }

}
*/
