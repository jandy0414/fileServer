package com.fs.fileServer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    public static boolean isNotBlank(String str) {
        if (str==null || "".equals(str) || str.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 获取两个日期之间的秒数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTimeDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000);
    }
}
