package com.fs.fileServer.controller;

import com.fs.fileServer.model.ValidatorParamer;
import com.fs.fileServer.util.StringUtil;
import com.fs.fileServer.model.FileConf;
import com.fs.fileServer.model.FileLog;
import com.fs.fileServer.model.viewModel.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {
    private static Logger logger= LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileConf fileConf;

    @PostMapping("/upload")
    public ResultJson updloadFile(String source,  MultipartFile file,String extFile){

        if (file == null || file.isEmpty()) {
            return ResultJson.Fail(fileConf.getNodeId(),source,"上传文件为空");
        }
        String valid_source=ValidatorParamer.validSource(source);
        if (!"isOK".equals(valid_source)) {
            return ResultJson.Fail(fileConf.getNodeId(),source,valid_source);
        }
        /**
         * 可由业务方自定义文件扩展名，默认为jpg
         */
        String extF="jpg";
        if (StringUtil.isNotBlank(extFile))
        {
            extF=extFile;
        }

        FileLog fileLog = new FileLog(source, extF, fileConf);
        try {
            fileLog.saveFile(file);
            String uri="/"+fileConf.getNodeId()+fileLog.getFilePath()+"/"+fileLog.getId()+"."+fileLog.getFileType();
            return ResultJson.OK(fileConf,fileLog,uri,"");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(fileConf.getNodeId()+":"+e.getLocalizedMessage());
            return ResultJson.Fail(fileConf,fileLog,"文件IO处理失败:"+e.getLocalizedMessage());
        }
    }

    @PostMapping("/uploadBase64")
    public ResultJson updloadFileBase64(String source,  String base64,String extFile){

        String valid_base64=ValidatorParamer.validBase64(base64);
        if (!"isOK".equals(valid_base64))
        {
            return ResultJson.Fail(fileConf.getNodeId(),source,valid_base64);
        }
        String valid_source=ValidatorParamer.validSource(source);
        if (!"isOK".equals(valid_source)) {
            return ResultJson.Fail(fileConf.getNodeId(),source,valid_source);
        }
        /**
         * 可由业务方自定义文件扩展名，默认为jpg
         */
        String extF="jpg";
        if (StringUtil.isNotBlank(extFile))
        {
            extF=extFile;
        }

        FileLog fileLog = new FileLog(source, extF, fileConf);
        try {
            fileLog.saveFileBase64(base64);
            String uri="/"+fileConf.getNodeId()+fileLog.getFilePath()+"/"+fileLog.getId()+"."+fileLog.getFileType();
            return ResultJson.OK(fileConf,fileLog,uri,"");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(fileConf.getNodeId()+":"+e.getLocalizedMessage());
            logger.error(fileLog.getId()+":"+base64);
            return ResultJson.Fail(fileConf,fileLog,"文件IO处理失败:"+e.getLocalizedMessage());
        }
    }

    @PostMapping("/uploadBase64Small")
    public ResultJson updloadFileBase64Small(String source,  String base64,String extFile,Integer scale){

        String valid_base64=ValidatorParamer.validBase64(base64);
        if (!"isOK".equals(valid_base64))
        {
            return ResultJson.Fail(fileConf.getNodeId(),source,valid_base64);
        }
        String valid_source= ValidatorParamer.validSource(source);
        if (!"isOK".equals(valid_source)) {
            return ResultJson.Fail(fileConf.getNodeId(),source,valid_source);
        }
        if (scale == null){
            scale=2;
        } else {
            String valid_scale= ValidatorParamer.validScale(scale);
            if (!"isOK".equals(valid_scale)) {
                return ResultJson.Fail(fileConf.getNodeId(),source,valid_scale);
            }
        }


        /**
         * 可由业务方自定义文件扩展名，默认为jpg
         */
        String extF="jpg";
        if (StringUtil.isNotBlank(extFile))
        {
            extF=extFile;
        }

        FileLog fileLog = new FileLog(source, extF, fileConf);
        try {
            fileLog.saveFileBase64(base64);

            //文件同时存小图处理
            String srcFilePath=fileConf.getDrive()+fileLog.getFilePath()+"/"+fileLog.getId()+"."+fileLog.getFileType();
            //设置小图的文件名
            String destFilePath=fileConf.getDrive()+fileLog.getFilePath()+"/"+fileLog.getId()+"_sm"+scale+"."+fileLog.getFileType();

            fileLog.zoomImage(srcFilePath,destFilePath,scale);//存小图
            String uri="/"+fileConf.getNodeId()+fileLog.getFilePath()+"/"+fileLog.getId()+"."+fileLog.getFileType();
            String uriSmall="/"+fileConf.getNodeId()+fileLog.getFilePath()+"/"+fileLog.getId()+"_sm"+scale+"."+fileLog.getFileType();
            return ResultJson.OK(fileConf,fileLog,uri,uriSmall);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(fileConf.getNodeId()+":"+e.getLocalizedMessage());
            logger.error(fileLog.getId()+":"+base64);
            return ResultJson.Fail(fileConf,fileLog,"文件IO处理失败:"+e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(fileConf.getNodeId()+":"+e.getLocalizedMessage());
            return ResultJson.Fail(fileConf,fileLog,"文件缩小处理时失败:"+e.getLocalizedMessage());
        }
    }
}
