package com.fs.fileServer.model;

import com.fs.fileServer.util.StringUtil;

import java.util.regex.Pattern;

public class ValidatorParamer {

    /**
     * 校验入参source的合法性
     * @param psource
     * @return
     */
    public static String validSource(String psource){
        if (!StringUtil.isNotBlank(psource)) {
            return "source是必须的参数";
        }
        if (psource.length()>10 || psource.length()<3){
            return "source须为3到10位的字符串";
        }
        if (!psource.matches("^[A-Za-z0-9]+$"))
        {
            return "source为正则匹配出错，应为字母或数字";
        }
        return  "isOK";
    }

    /**
     * 校验base64的合理性
     */
    public static String validBase64(String base64) {
        if (!StringUtil.isNotBlank(base64)) {
            return "base64数据串为空";
        }
        if(!base64.startsWith("/9j")) {
            return "正确图片的的Base64是以 /9j 开头了";
        }
        if(base64.length()>4000000) {
            return "Base64串字符超过4百万个，图片太大了";
        }

        return "isOK";
    }

    /**
     * 验证，图片所放比例scale
     */
    public static String validScale(Integer scale){

        if (scale!=2 && scale!=3 && scale!=4 && scale!=5)
        {
            return "目前支持的缩小比例必须是：2,3,4,5倍的缩小,而输入是："+scale;
        }

        return "isOK";
    }

    public static void main(String[] args) {
        String str="ivr2";
        String out=validSource(str);
        System.out.println(out);
    }



}
