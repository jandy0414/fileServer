package com.fs.fileServer.model.viewModel;

import com.fs.fileServer.model.FileConf;
import com.fs.fileServer.model.FileLog;

public class ResultJson {

    private int status;

    private String message;

    private String fileId;

    private String hosts;

    private String uri;

    private String uriSmall;

    private String nodeId;

    private String source;

    private String errorMsg;



    public ResultJson(int status, String message, String fileId, String hosts, String uri,String uriSmall, String nodeId, String source,  String errorMsg) {
        this.status = status;
        this.message = message;
        this.fileId = fileId;
        this.hosts = hosts;
        this.uri = uri;
        this.uriSmall=uriSmall;
        this.nodeId = nodeId;
        this.source = source;
        this.errorMsg = errorMsg;
    }

    public static ResultJson OK(FileConf fileConf, FileLog fileLog, String uri,String uriSmall) {

        return new ResultJson(200,"操作成功", fileLog.getId(), fileConf.getResHosts(),uri,uriSmall, fileConf.getNodeId(),fileLog.getSource(),"");
    }

    public static ResultJson Fail(String nodeId, String source,String errorMsg) {
        ResultJson resJson=new ResultJson (500,"操作失败","","","","",nodeId,source,errorMsg);
        return resJson;
    }

    public static ResultJson Fail(FileConf fileConf, FileLog fileLog,String errorMsg) {
        ResultJson resJson=new ResultJson (500,"操作失败",fileLog.getId(),"","","",fileConf.getNodeId(),fileLog.getSource(),errorMsg);
        return resJson;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUriSmall() {
        return uriSmall;
    }

    public void setUriSmall(String uriSmall) {
        this.uriSmall = uriSmall;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
