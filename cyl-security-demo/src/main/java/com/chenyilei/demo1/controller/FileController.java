package com.chenyilei.demo1.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/28- 15:17
 */
@RestController
public class FileController {

    @PostMapping("/file")
    public Object fileUpload(){
        return "1";
    }

    @GetMapping("/getFile")
    public void getFileUpload(HttpServletResponse response) throws Exception{
        final String localTxt = System.getProperty("user.dir")+"/cyl-security-demo/src/main/resources/error/503.html";

        InputStream inputStream = new FileInputStream(new File(localTxt));
        OutputStream outputStream = response.getOutputStream();

        response.addHeader("Content-Type","application/x-download");
        response.addHeader("Content-Disposition","attachment;filename=test.html");
        IOUtils.copy(inputStream,outputStream);


        outputStream.flush();
        response.flushBuffer();
        return ;
    }
}
