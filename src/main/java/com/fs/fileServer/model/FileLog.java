package com.fs.fileServer.model;

import com.fs.fileServer.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * 文件存储日志记录
 */
public class FileLog {

    private String id;

    private String source;

    private String filePath;

    private String fileType;

    //默认为1，扩展性字段考虑
    private int status;

    private String createTime;

    private FileConf fileConf;

    private static Logger logger= LoggerFactory.getLogger(FileLog.class);

    /**
     * 初始构造，在controller 发起
     * @param source
     * @param extFile
     * @param fileConf
     */
    public FileLog(String source, String extFile,FileConf fileConf) {
        this.source = source;
        this.fileConf = fileConf;
        this.setFileType(extFile);

        //生成文件ID
        this.setId(UUID.randomUUID().toString().replace("-", ""));

    }

    /**
     * 保存上传的baset64图片信息
     * @param base64
     * @throws IOException
     */
    public void saveFileBase64(String base64) throws  IOException {
        Date start = new Date();
        Date end = null;
        byte [] imageByte = null;
        try {
            imageByte = Base64.getDecoder().decode(base64);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            end = new Date();
            double a = StringUtil.getDistanceOfTimeDate(start, end);
            if(a >= 20){
                logger.error("间隔："+a+" strat:"+start+" end:"+end);
            }
           throw new IOException("base64转换成字节码失败，耗时"+a+"秒");
        }

        saveBytetoFile(imageByte);
    }

    /**
     * 图边字节码存为物理文件
     * @param imageByte
     * @throws IOException
     */
    private  void  saveBytetoFile(byte [] imageByte) throws IOException {
        OutputStream out = null;
        Date start=new Date();
        Date end=null;
        String filePath="";
        try {
            filePath=mkFileDir();
            out = new FileOutputStream(filePath);
            out.write(imageByte);
            out.flush();
            end = new Date();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage());
            end = new Date();
            double a = StringUtil.getDistanceOfTimeDate(start, end);
            if(a >= 20){
                logger.error("间隔："+a+" strat:"+start+" end:"+end+"save FileOutputStream");
            }
            throw new IOException("图片生成到指定目录失败，path："+filePath);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    /*
     * 图片缩放,scale为缩小比例
     * src为源文件目录，dest为缩放后保存目录
     */
    public  void zoomImage(String src,String dest,int scale) throws Exception {

        File srcFile = new File(src);
        File destFile = new File(dest);

        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        int cuW=bufImg.getWidth();
        int cuH=bufImg.getHeight();
        Image Itemp = bufImg.getScaledInstance(cuW/scale, cuH/scale, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板


        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(1.0/scale, 1.0/scale), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile); //写入缩减后的图片
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("图片进行缩小时出现异常，scale："+scale+" destWidth:"+cuW/scale+" destHeight:"+cuH/scale);
            throw new Exception("图片进行缩小时出现异常，scale："+scale+" destWidth:"+cuW/scale+" destHeight:"+cuH/scale);
        }
    }


    /**
     * 储存二进制上来的文件
     * @param file
     * @throws IOException
     */
    public void saveFile(MultipartFile file) throws IOException {

        //获取文件类型
        if (!StringUtil.isNotBlank(this.getFileType())){
            this.setFileType("jpg");
        }

        //生成文件目录
        String fullFilePath=mkFileDir();

        /**
         * 保存文件
         */
        file.transferTo(new File(fullFilePath));


    }

    /**
     * 获取本地路径, 做物理存储用
     * @Title: getUrl
     * @throws
     */
    private String mkFileDir() throws IOException {
        String filePath1=this.fileConf.getDrive();
        String filePaht2=this.getSource();
        String filePath3=StringUtil.getDate("yyyy/MM/dd");
        String dir=null;
        if(StringUtil.isNotBlank(filePath1) && StringUtil.isNotBlank(filePaht2) && StringUtil.isNotBlank(filePath3)){
            this.setFilePath("/"+filePaht2+"/"+filePath3);
            dir=filePath1+"/"+filePaht2+"/"+filePath3+"/";

        }

        if(!StringUtil.isNotBlank(dir)){
            throw new IOException("路径有错，dir:"+dir);
        }
        if(!StringUtil.isNotBlank(this.getId())) {
            throw new IOException("文件名有错，fileId:"+this.getId());
        }
        if(!StringUtil.isNotBlank(this.getFileType())) {
            throw new IOException("文件扩展名有错，fileType:"+this.getFileType());
        }

        /**
         * 创建目录
         */
        mkdirFile(dir);

        return dir+this.getId()+"."+this.getFileType();
    }

    /**
     * 新建文件的目录
     * @Title: mkdirFile
     * @Description: TODO描述
     * @param @param url
     * @return void
     * @author Wille.SU
     * @throws
     */
    private void mkdirFile(String url) throws IOException {
        File file = new File(url);
        if(!file.exists()){
            mkdirFile(file.getParent());
            file.mkdir();
        }
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }




    @Override
    public String toString() {
        return "FileLog{" +
                "id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", fileConf=" + fileConf +
                '}';
    }
}
