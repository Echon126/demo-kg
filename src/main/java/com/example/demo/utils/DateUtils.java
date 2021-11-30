package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

//    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        Date birthday = sdf.parse("2020-08-13 13:13:13");
//        System.out.println("birthday = " + birthday.toString());
//        System.out.println("birthday = " + birthday.getTime());
//
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        Date birthday1 = sdf1.parse("2020-08-13 13:13:13");
//        System.out.println("birthday = " + birthday1.toString());
//        System.out.println("birthday = " + birthday1.getTime());
//    }


    /**
     * local时间转换成UTC时间
     *
     * @param localTime
     * @return
     */
    public static Date localToUTC(long localTimeInMillis) {
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }

    public static void main(String[] args) {
        Date date = localToUTC(System.currentTimeMillis());
        System.out.println(date);

        byte[] decode = Base64.getDecoder().decode("15620742723");


    }

}
