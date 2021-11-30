package com.example.demo.utils;

import com.mysql.cj.xdevapi.SessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;

@Service
public class ScriptRunUtils {
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    public void runSqlBySpringUtils() throws Exception {
//        try {
//
//            SqlSession sqlSession = sessionFactory.getSession();
//            Connection conn = sqlSession.getConnection();
//            //ClassPathResource rc = new ClassPathResource("脚本.Sql", RunSqlDao.class);
//            //EncodedResource er = new EncodedResource(rc, "utf-8");
//
//            InputStreamResource inputStreamResource = new InputStreamResource();
//            ScriptUtils.executeSqlScript(conn, inputStreamResource);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
    }
}
