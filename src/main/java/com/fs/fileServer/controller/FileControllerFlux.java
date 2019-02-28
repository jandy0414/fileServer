package com.fs.fileServer.controller;

import com.fs.fileServer.util.StringUtil;
import com.fs.fileServer.model.FileConf;
import com.fs.fileServer.model.FileLog;
import com.fs.fileServer.model.viewModel.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class FileControllerFlux {

    @Autowired
    private FileConf fileConf;

    @PostMapping("/flux/upload")
    public Mono<ResultJson> updloadFile(String source, MultipartFile file){

        if (file == null || file.isEmpty()) {
            ResultJson resJson=ResultJson.Fail(fileConf.getNodeId(),source,"上传文件为空");
            return Mono.just(resJson);
        }
        if (!StringUtil.isNotBlank(source)) {
            ResultJson resJson=ResultJson.Fail(fileConf.getNodeId(),source,"source是必须的参数");
            return Mono.just(resJson);
        }

        FileLog fileLog = new FileLog(source, "jpg", fileConf);
        try {
            fileLog.saveFile(file);
            String uri=fileLog.getFilePath()+"/"+fileLog.getId()+"."+fileLog.getFileType();
            ResultJson resJson=ResultJson.OK(fileConf,fileLog,uri,"");
            return Mono.just(resJson);
        } catch (IOException e) {
            e.printStackTrace();
            ResultJson resJson=ResultJson.Fail(fileConf.getNodeId(),source,"文件IO处理失败:"+e.getLocalizedMessage());
            return Mono.just(resJson);
        }
    }
}
