package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
public class FileController {
    @Value("${file.upload-path}")
    private String uploadDir;

    @PostMapping("/file/img/upload")
    @ResponseBody
    public Object uploadFile(@RequestParam("img") MultipartFile multipartFile){
        String originalName = multipartFile.getOriginalFilename();
        String name = originalName.substring(0,originalName.lastIndexOf("."));
        String postFix = originalName.substring(originalName.lastIndexOf(".")+1);
        String fileName = name+System.currentTimeMillis()+"."+postFix;
        File parentPath = new File(uploadDir+"/img");
        if (!parentPath.exists()){
            parentPath.mkdir();
        }
        File file = new File(parentPath,fileName);
        try {
            boolean success = file.createNewFile();
            if (!success){return new Result(ResultConstant.CODE_FAILED,ErrorConstant.FILE_UPLOAD_ERROR,null);}
            multipartFile.transferTo(file);
        }
        catch (IOException e){
            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.FILE_UPLOAD_ERROR,null);
        }
//        InputStream in = multipartFile.getInputStream();
//        FileOutputStream out = null;
//        out = new FileOutputStream(file);
//        int read = -1;
//        byte[] data = new byte[1024*10];
//        while ((read = in.read(data))!=-1){
//            out.write(data,0,read);
//        }
//        out.close();
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,"upload/img/"+fileName);
    }

}
