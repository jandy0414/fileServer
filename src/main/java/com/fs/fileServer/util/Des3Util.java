package com.fs.fileServer.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

//import org.apache.commons.codec.binary.Base64;

public class Des3Util {

    private static String text="{\"face_image_id\":\"33EDC539-F383-6BDC-E468-20E2ba36964C\","
            + "\"feature_code\":\"123456\","
            + "\"picture_name\":\"35020677001111700000120181101101010123F\" ,"
            + "\"picture_md5\":\"4C35DEED1AFD94154C0B02C311334974\","
            + "\"identification_type\":\"01\","
            + "\"identification_number\":\"350624198504042512\","
            + "\"Gender\":1,"
            + "\"Name\":\"吴晓龙\","
            + "\"Nation\":\"01\","
            + "\"Region\":\"350624\","
            + "\"born_date\":\"19850404\","
            + "\"timestamp\": 1543893165,"
            + "\"Phone\":\"18250206244\","
            + "\"cj_ga_code\":\"350602770000\","
            + "\"cj_ga_name\":\"漳州市公安局芝山派出所\","
            + "\"st_address_code\":\"32EDC739-F303-4BDC-E054-90E2ba54908C\","
            + "\"st_address\":\"福建省漳州市芗城区惠民路9号\","
            + "\"st_address_gps\":[117.627248,24.532877],"
            + "\"city_code\":\"350600\","
            + "\"city_name\":\"漳州市\","
            + "\"area_code\":\"350602\","
            + "\"area_name\":\"芗城区\","
            + "\"toponym_code\":\"350600120000000709423\","
            + "\"toponym_name\":\"惠民路\","
            + "\"street_code\":\"350206770\","
            + "\"street_name\":\"芝山镇\","
            + "\"committees_code\":\"350602008031\","
            + "\"committees_name\":\"金永社区居民委员会\","
            + "\"ga_code\":\"350602770000\","
            + "\"ga_name\":\"漳州市公安局芝山派出所\","
            + "\"ga_grid_code\":\"350600120000000089815\","
            + "\"ga_grid_name\":\"漳州市公安局芝山派出所第三警务网格\"}";

    /**
     * 3DES加密
     *
     * @param data
     *            数据
     * @param key
     *            密钥
     * @return
     */
    public static String encode(String data, String key) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
            return Base64.encodeBase64String(cipher.doFinal(data.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String encodeByte(String data, byte[] key) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
            return Base64.encodeBase64String(cipher.doFinal(data.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encodeByteOutByte(String data, byte[] key) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
            byte[] outByte=cipher.doFinal(data.getBytes("utf-8"));
            return outByte;
//            return Base64.encodeBase64String(cipher.doFinal(data.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String hexstr  = "296CD6EB2CA94321ABEF575F4CFC10EC296CD6EB2CA94321";
        byte[] bKey=hexStringToByteArray(hexstr);
        System.out.println(text);
        System.out.println("len:"+text.length());
        byte[] encText=encodeByteOutByte(text,bKey);
        saveBytetoFile(encText);
//        String filePath = "C:/upload/BasicFaceData-20150623212251.json";
        String filePath = "C:/upload/BasicFaceData-20150623213300.json";
        byte[] decTextByte=getBytes(filePath);
        String decStr=decodeByteByByte(decTextByte,bKey);
        System.out.println("----------------------------");
        System.out.println(decStr);
        System.out.println("----------------------------");

//        String encText="oauY2OGdeYEW101JNaVcIja5RJHrZtsCuNgROxb6PFuZZPi79KO/+duH4UiK4i8ZBA892S/R57HojnIjuR6WX3846iWpMzlLYihbakdlCaPxLiFtBTb3hXs6Bj3djqJjaFW6mnhphDXkPRLkmC9gliCoKBOpLCHIlmj0XvZ8XC+1wkoYvGQEeNuXDlzDZbC8CUdjkOh7aQBbtpWPEsq/pF1CD3sRXKDyMtuLY+pGlEt+bVyn3sd4IpbhPMXzrCZbimfplHqwFYlwf+UoYE9GAbBvyVpTy9w3jk/qWOMxs+OEQIzkPH8OBpEyOcgmzv8oiNBdidDv0MHVAd3x+J5A2udKD7/VNT9LFLBswCELjO73sERsRmSDUpSu82lO7u/5+iS+AeIeRm5JSK9NRlf3xU1Ssu+Ada5bo8kLYaeU/KI7I2gp92xJ7ddr6yp5NY8Imtr/bFOz35nFDahkWfZiVPyL+HtakljEDAQL3wbF6HCDv/dAh1kWxMWg9YWPEdzhW/0ZBiRLeyV59ODT7vFNhoeRu1KUMczhO54j4ZhyX0S8/VVK67MgN3s6Bj3djqJje1jFQGgv7hXH4V3iI+usUnAHmfXcqaD806kHx9zByTy0mgpyuALyxYlf/EsQVEvvjQ2nxgx1uknQCmCcuES80xNfHPeDN7/xoJ9eruiUtwMXBpHjcPuhOId4G3/XWM5DQYxSZdVQdpJJxl0Va7/GzFcUnztDK1/ahNSuvm7Ul8aAbDOZwYJ5+/S7p1NSn5WvBYBQQwF3Sctu/F3rRxJyrNwcAHLzyK8yWNhYEUlixnGzABYDYXBxoSWG4hs53sxLpCWpe0Mo7zmESTfNcd1FTNOmts0cLOMGuLONkrLrAWW3HexqOpcG8D3ryjqXEgBePtNoQznxeHtcjbmNCcfeGmUyDtYe6eaOg/H1Q8keDfmpJnFOme6GOo5CGAUEMFiFPtNoQznxeHuNrXhs/+Y5/VklFhY23wz3m8RGxOhAOwuY0RCGsUheI49jViz+I/ZQXCuOaJ8of2UW1SQrGmnQQgHy5evOo6gNnkWIQHKmumHCmKxapvstOU2oDylR5ICpSKi3eIY9OVbSlAFC9sKx5DV0n29g1xk4AfLl686jqA3/p04acenHyKcVfPGUMORyoQGw88hiit57OgY93Y6iYyd+KnQleITj6LJk3ueiDVSxz+L6eDGJusgIdQsmx3+BfzjqJakzOUupJjqFPKnCx5qjf2LI6QMAw6NEWl9QDYxMBWN6KE5N+bZQMMqV8lS7F4ozEqHnpj9t8ZoTo9Cuphy3hBM9HzY5r+Be/CN/jpeHkbtSlDHM4Yh+8ixptsFJfhbZGS+9sRFdO53EUSK+pCMMZPJGBrWWWugIdwbr0+tBqpHY1jdElLDPL5VrsfmCj4HK4Ooc+6ktJ4u42SZwJhdZ3aY368+a5E+LeJUMrpENtfAC2pnrtQ==\n";
//        System.out.println("");
//        System.out.println(encText);
//        System.out.println("----------------------------");
//        String s=decodeByte(encText,bKey);
//        System.out.println(s);

//        String hst="296CD6EB2CA94321ABEF575F4CFC10EC";
//        String str=hexStr2Str(hst);
//        System.out.println(str);
    }
    private static   void  saveBytetoFile(byte [] imageByte) throws IOException {
        OutputStream out = null;
        Date start = new Date();
        Date end = null;
        String filePath = "C:/upload/BasicFaceData-20150623212251.json";
        try {
            out = new FileOutputStream(filePath);
            out.write(imageByte);
            out.flush();
            end = new Date();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            end = new Date();
            double a = StringUtil.getDistanceOfTimeDate(start, end);

            throw new IOException("生成到指定目录失败，path：" + filePath);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


//    public static void main(String[] args) {
////        String minganKey = "OPJURIZFIWZWJIFDNXUXWSMFILOCLJQK";
//        String minganKey = "9343776C23C46074007D65B276F96365";
////        String strhex=str2HexStr(minganKey);
////        System.out.println("====:"+strhex+":====");
//        String hexstr  = "296CD6EB2CA94321ABEF575F4CFC10EC296CD6EB2CA94321";
//        byte[] bKey=hexStringToByteArray(hexstr);
////        System.out.println("key:"+minganKey);
//
//        System.out.println(text);
//        System.out.println("len:"+text.length());
////        String encText=encode(text,minganKey);
//        String encText=encodeByte(text,bKey);
//        System.out.println("");
//        System.out.println(encText);
//        System.out.println("----------------------------");
//
////        System.out.println(minganKey);
////        System.out.println("dy9OMCtSK1ZoZ0FMUDZOWjUwQ2tlLzZvN2xKNVN4bFMwSFQ0YVY0QXBWUHAzYW84YjViWS9ydEY2U1JEUjlmV1hkcjhYMG1veGs3QXdzSmxHQ0QxNno3NXhha3hxdlkyN0U5U0EyQWxKNUk9".length());
////        String s=decode("dy9OMCtSK1ZoZ0FMUDZOWjUwQ2tlLzZvN2xKNVN4bFMwSFQ0YVY0QXBWUHAzYW84YjViWS9ydEY2U1JEUjlmV1hkcjhYMG1veGs3QXdzSmxHQ0QxNno3NXhha3hxdlkyN0U5U0EyQWxKNUk9", minganKey);
////        String s=decode(encText,minganKey);
//        String s=decodeByte(encText,bKey);
//        System.out.println(s);
//    }
    /**
     * 3DES解密
     *
     * @param key
     *            密钥
     * @param data
     *            密文
     * @return
     */
    public static String decode(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
            SecretKey sk = SecretKeyFactory.getInstance("DESede").generateSecret(dks);
            cipher.init(Cipher.DECRYPT_MODE, sk);
            byte[] result = cipher.doFinal(Base64.decodeBase64(data));
            return new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String decodeByte(String data, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            DESedeKeySpec dks = new DESedeKeySpec(key);
            SecretKey sk = SecretKeyFactory.getInstance("DESede").generateSecret(dks);
            cipher.init(Cipher.DECRYPT_MODE, sk);
            byte[] result = cipher.doFinal(Base64.decodeBase64(data));
            return new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String decodeByteByByte(byte[] data, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS7Padding");
            DESedeKeySpec dks = new DESedeKeySpec(key);
            SecretKey sk = SecretKeyFactory.getInstance("DESede").generateSecret(dks);
            cipher.init(Cipher.DECRYPT_MODE, sk);
            byte[] result = cipher.doFinal(data);
            return new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] hexStringToByteArray(String text) {
        if (text == null)
            return null;
        byte[] result = new byte[text.length() / 2];
        for (int i = 0; i < result.length; ++i) {
            int x = Integer.parseInt(text.substring(i * 2, i * 2 + 2), 16);
            result[i] = x <= 127 ? (byte) x : (byte) (x - 256);
        }
        return result;
    }

    public static byte[] hexStr2Bytes(String hexStr) {
        String str ="0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes =new byte[hexStr.length() /2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return bytes;
    }

    public static String hexStr2Str(String hexStr) {
        String str ="0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes =new byte[hexStr.length() /2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }



    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }

    public static String byteArrayToHexString(byte data[]) {
        String result = "";
        for (int i = 0; i < data.length; i++) {
            int v = data[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                result += "0";
            }
            result += hv;
        }
        return result;
    }

}