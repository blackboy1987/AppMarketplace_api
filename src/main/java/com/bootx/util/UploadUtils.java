package com.bootx.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class UploadUtils {

    private static final String ENDPOINT = "https://oss-cn-hangzhou.aliyuncs.com";
    private static final String BUCKET_NAME = "bootxyysc";
    private static final String ACCESS_KEY_ID = "LTAI5tGYCpDRGU1s3VZjydcz";
    private static final String SECRET_ACCESS_KEY = "lgTgxgtkaG4RDgxqwDbzk4W82VPUGG";
    private static final String URL_PREFIX = "https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/";

    public static String upload(String path, File file){
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID,SECRET_ACCESS_KEY);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, path, FileUtils.openInputStream(file));
            PutObjectResult result = ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException | IOException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }

        }
        return URL_PREFIX+path;
    }
}