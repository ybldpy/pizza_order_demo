package com.example.pizza_order_demo.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;

public class FileUploadUtil {

    public static String uploadFile(String accessKey, String secretKey, String bucket, String bucketName, String path, String filePath, File file){
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        COSClient cosclient = new COSClient(cred, clientConfig);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,filePath,file);
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            return path+filePath;
        }
        catch (Exception e){
            return null;
        }
        finally {
            file.delete();
            cosclient.shutdown();
        }

    }

}
