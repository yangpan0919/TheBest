package com.best.zcdn.controller;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.best.zcdn.interfaces.FileUploadServiceI;
import com.sun.deploy.net.URLEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * Created with com.zhiren.emp.controller.
 * User: jiangbin
 * Date: 2018/10/29
 * Time: 11:43
 */
@RestController
@RequestMapping("/hello")
public class HelloController {


    @Resource
    private FileUploadServiceI fileUploadService;



 @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public String fileUpload(MultipartHttpServletRequest request){
     MultipartFile multipartFile = request.getFile("fileUpload");
     // Endpoint以杭州为例，其它Region请按实际情况填写。
     String endpoint ="tokenWeb";
     // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     String accessKeyId = "";
     String accessKeySecret = "";
     String bucketName = "csplatform-dev";
     String objectName = "1/2/3/1234.xlsx";
     OSSClient ossClient = null;

     try{

         // 创建OSSClient实例。
         ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
         ObjectMetadata ioe = new ObjectMetadata();
         ioe.setHeader("Content-Disposition","mmp");
         OSSObject object = ossClient.getObject(bucketName, objectName);
//         ioe.setContentDisposition("attachment");
//         ossClient.putObject(bucketName,"1/2/3/1234.xlsx",multipartFile.getInputStream(),ioe);
         // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
//         ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("D:\\学习\\study.xlsx"));

     }catch(Exception e) {
         System.out.println("+++++++++++++++++++出错了+++++++++++++++++++");
     }finally {
         if(ossClient!=null){
             // 关闭OSSClient。
             ossClient.shutdown();
         }
     }
//     MultipartFile multipartFile = request.getFile("fileUpload");
//     FileUploadMessageDTO fileUploadMessageDTO = fileUploadService.uploadFile(multipartFile, "KEY"+"/"+"PROC_INST_ID"+"/"+"TASK_ID"+"/", true);
//     System.out.println("-------------------------------------"+fileUploadMessageDTO.getUrl());
//     System.out.println(fileUploadMessageDTO.getSrcName());
     return "SUCESS";
    }
    @RequestMapping(value = "/fileUpload1", method = RequestMethod.GET)
    public String fileUpload1(HttpServletResponse response){

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint ="tokenWeb";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "csplatform-dev";
        String objectName = "1/2/3/1234.xlsx";
        //文件名
        String fileName = "qwesa456.xlsx";       /////////////////////////////////////////////设置文件呢下载时的文件名
        try {
            fileName =  URLEncoder.encode(fileName,"UTF-8");   //支持中文编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        String fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition",
                "attachment; filename=" + fileName);
        response.addHeader("Content-Type", "application/binary");
        OSS ossClient = null;
//        OSSClient ossClient = null;
        ServletOutputStream outputStream = null;
        try{
         // 创建OSSClient实例。

            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
         // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
//         ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("D:\\download\\study.xlsx"));

            //将输入流转化成输出流
         OSSObject object = ossClient.getObject(bucketName, objectName);
         //读取导出参数
         outputStream = response.getOutputStream();
         InputStream in = object.getObjectContent();

         byte[] buf = new byte[1024];
         int len = in.read(buf);
         while(len != -1) {
             outputStream.write(buf,0,len);
             len = in.read(buf);
         }

         outputStream.flush();

     }catch(Exception e) {
         System.out.println("+++++++++++++++++++出错了+++++++++++++++++++");
     }finally {
         if(outputStream!=null){
             try {
                 outputStream.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         if(ossClient!=null){
             // 关闭OSSClient。
             ossClient.shutdown();
         }
     }
        return "SUCESS";
    }
    @RequestMapping(value = "/fileUpload2", method = RequestMethod.GET)
    public String fileUpload2(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint ="tokenWeb";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "csplatform-dev";
        String objectName = "test1/8af6ec73b9024f15bb3f7ac042ecd92a.xlsx";
        OSSClient ossClient = null;
        BufferedReader reader = null;
        try{
            // 创建OSSClient实例。
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            // 读取文件内容。
            System.out.println("Object content:");
            reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                System.out.println("\n" + line);
            }

        }catch(Exception e) {
            System.out.println("+++++++++++++++++++出错了+++++++++++++++++++");
        }finally {
            if(ossClient!=null){
                // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
                try {
                    if(reader!= null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        return "SUCESS";
    }
    @RequestMapping(value = "/fileUpload3", method = RequestMethod.GET)
    public String fileUpload3(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint ="tokenWeb";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "csplatform-dev";
        String key = "test1/8af6ec73b9024f15bb3f7ac042ecd92a.xlsx";
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {

            /**
             * Note that there are two ways of uploading an object to your bucket, the one
             * by specifying an input stream as content source, the other by specifying a file.
             */

            /*
             * Upload an object to your bucket from an input stream
             */
            System.out.println("Uploading a new object to OSS from an input stream\n");
            String content = "Thank you for using Aliyun Object Storage Service";
            client.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));

            /*
             * Upload an object to your bucket from a file
             */
            System.out.println("Uploading a new object to OSS from a file\n");
            client.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));

            /*
             * Download an object from your bucket
             */
            System.out.println("Downloading an object");
            OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
            object.getObjectContent();
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            displayTextInputStream(object.getObjectContent());

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Exception ce) {
            System.out.println("+++++++++++++++++++出错了+++++++++++++++++++");
        } finally{
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
        return null;
    }


    private File createSampleFile() throws IOException {
        File file = File.createTempFile("oss-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("0123456789011234567890\n");
        writer.close();

        return file;
    }

    private  void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("\t" + line);
        }
        System.out.println();

        reader.close();
    }
}