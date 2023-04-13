package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

@Controller
public class FileController {
    @Value("${file.upload-path}")
    private String uploadDir;

    @PostMapping("/file/img/upload")
    @ResponseBody
    public Object uploadFile(MultipartFile multipartFile) throws FileNotFoundException {
    }

}
