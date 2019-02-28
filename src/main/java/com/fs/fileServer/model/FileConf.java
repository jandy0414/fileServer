package com.fs.fileServer.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 服务节点，与具体活跃目录的配置
 *
 */
@Configuration
@ConfigurationProperties(prefix="default.fileconf")
@PropertySource(value="classpath:conf.properties")
public class FileConf {

    private String nodeId;

    private String IP;

    private int port;

    private String drive;

    private String resHosts;


    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String id) {
        this.nodeId = id;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }



    public String getResHosts() {
        return resHosts;
    }

    public void setResHosts(String resHosts) {
        this.resHosts = resHosts;
    }


    @Override
    public String toString() {
        return "FileConf{" +
                "nodeId='" + nodeId + '\'' +
                ", IP='" + IP + '\'' +
                ", port=" + port +
                ", drive='" + drive + '\'' +
                ", resHosts='" + resHosts + '\'' +
                '}';
    }
}
