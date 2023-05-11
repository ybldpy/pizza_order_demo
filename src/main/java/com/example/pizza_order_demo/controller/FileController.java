package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.model.Log;
import com.example.pizza_order_demo.model.LogExample;
import com.example.pizza_order_demo.service.LogService;
import com.example.pizza_order_demo.utils.FileUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class FileController {
    @Value("${file.temp-path}")
    private String tempDir;

    @Value("${tengxun.accessKey}")
    private String accessKey;
    @Value("${tengxun.secretKey}")
    private String secretKey;
    @Value("${tengxun.bucket}")
    private String bucket;
    @Value("${tengxun.bucketName}")
    private String bucketName;
    @Value("${tengxun.path}")
    private String basePath;
    @Value("${tengxun.prefix}")
    private String prefix;


    @Autowired
    private LogService logService;

    @PostMapping("/file/img/upload")
    @ResponseBody
    public Object uploadFile(@RequestParam("img") MultipartFile multipartFile) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        String name = originalName.substring(0,originalName.lastIndexOf("."));
        String postFix = originalName.substring(originalName.lastIndexOf(".")+1);
        String fileName = name+System.currentTimeMillis()+"."+postFix;
        File pFile = new File(tempDir);
        if (!pFile.exists()){
            pFile.mkdirs();
        }
        File tempFile = new File(tempDir+"/"+fileName);
        tempFile.createNewFile();
        multipartFile.transferTo(tempFile);
        String uploadedPath = FileUploadUtil.uploadFile(accessKey,secretKey,bucket,bucketName,basePath,prefix+"/"+ UUID.randomUUID()+"."+postFix,tempFile);
        if (StringUtils.isBlank(uploadedPath)){
            return new Result(ResultConstant.CODE_FAILED,"Upload failed",null);
        }

        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,uploadedPath);
//        InputStream in = multipartFile.getInputStream();
//        FileOutputStream out = null;
//        out = new FileOutputStream(file);
//        int read = -1;
//        byte[] data = new byte[1024*10];
//        while ((read = in.read(data))!=-1){
//            out.write(data,0,read);
//        }
//        out.close();
    }




}
