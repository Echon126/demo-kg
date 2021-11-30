package com.example.demo.example;

import java.io.File;

public class EasyExcelUtils {

    /**
     * 最简单的填充
     *
     * @since 2.1.1
     */
//
//    public  static void simpleFill() {
//        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
//        String templateFileName =TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "simple.xlsx";
//
//        // 方案1 根据对象填充
//        String fileName = TestFileUtil.getPath() + "simpleFill" + System.currentTimeMillis() + ".xlsx";
//        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
//        FillData fillData = new FillData();
//        fillData.setName("张三");
//        fillData.setNumber(5.2);
//        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(fillData);
//
//        // 方案2 根据Map填充
//        fileName = TestFileUtil.getPath() + "simpleFill" + System.currentTimeMillis() + ".xlsx";
//        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("name", "张三");
//        map.put("number", 5.2);
//        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
//    }
}
