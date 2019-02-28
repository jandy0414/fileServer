package com.fs.fileServer.controller;

import com.fs.fileServer.model.FileConf;
import com.fs.fileServer.model.viewModel.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * 路由器函数 配置
 */

@Configuration
public class FileControllerNIO {

    /**
     * Servlet
     * 请求接口：ServletRequest 或者 HttpServletRequest
     * 响应接口：ServletResponse 或者 HttpServletResponse
     *
     * Spring 5.0 重新定义了服务请求和响应接口：
     * 请求接口：ServerRequest
     * 响应接口：ServerResponse
     * 即可以支持  Servlet 规范，也可以支持自定义，比如 Netty (Web Server)
     *
     * 定义GET请求，并且还回所有的用户对象 URI: /person/find/all
     * Flux 是 0 ~ N个对象集合
     * Mono 是0 ~ 1 个对象集合
     * Reactive 中的Flux 或者Mono 它是异步处理 （非阻塞）
     * 集合对象基本上是同步处理（阻塞）
     *
     * Flux 或者是 Mono 都是Publisher
     *
     */

    @Bean
    @Autowired
    public RouterFunction<ServerResponse> FileUpload(@Autowired FileConf fileConf) {


        return RouterFunctions.route(RequestPredicates.POST("/nio/upload"),
            request ->{
              System.out.printf(fileConf.toString());

//                if (!request.headers().contentType().isPresent) {
//                    ResultJson resJson=ResultJson.Fail(fileConf.getNodeId(),"","没有headers");
//                    return ServerResponse.badRequest().body(Mono.just(resJson),ResultJson.class);
//                }

                boolean isMultipartFormData = request.headers().contentType().get().includes(MediaType.MULTIPART_FORM_DATA);
                if (!isMultipartFormData) {
                    ResultJson resJson=ResultJson.Fail(fileConf.getNodeId(),"",
                            "Request must have mediaType ${MediaType.MULTIPART_FORM_DATA_VALUE}");
                    return ServerResponse.badRequest().body(Mono.just(resJson),ResultJson.class);
                }


//                boolean allFilePartsMono = request.body(BodyExtractors.toMultipartData())
//                        .map(this::checkFileToUploadExists)
//                        .flatMap(this::waitForRemainingParts)
//                        .doOnSuccess(this::checkFileSize)
//                        .flux()
//                        .share();

                Mono<FileConf> sfileConf = Mono.just(fileConf);
              return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(sfileConf,FileConf.class);
        });
    }
}
