package com.best.zcdn.controller;

import com.aliyuncs.http.HttpResponse;
import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by EDZ on 2018/11/19.
 */
@RestController
public class TestController {


    @RequestMapping(value = "/fdsegrht", method = RequestMethod.POST)
    public String test(HttpRequest request, HttpResponse response) {


        return null;
    }

    @RequestMapping(value = "/insertT", method = RequestMethod.POST)//添加 实现单文件和多文件上传//MultipartFile file
    public String insertT(MultipartHttpServletRequest request) {
        String originalFilename1 = new String();
        List<MultipartFile> fileList1 = request.getFiles("fileTest");
        if (fileList1.size() > 0) {
//遍历文件列表
            Iterator<MultipartFile> fileTte = fileList1.iterator();
            while (fileTte.hasNext()) {
//获得每一个文件
                MultipartFile multipartFile = fileTte.next();
//获得原文件名
                originalFilename1 = multipartFile.getOriginalFilename();
                String realPath = request.getSession().getServletContext().getRealPath("/upload/tongzhi");
                File dir = new File(realPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filePath = realPath + "/" + originalFilename1;
                System.out.println("filePath:" + filePath);
//保存文件
                File dest = new File(filePath);
                if (!(dest.exists())) {
                    try {
                        multipartFile.transferTo(dest);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("_------------------------------------------------");
            }
        }
        StringBuffer originalFilename = new StringBuffer();
        List<MultipartFile> fileList = request.getFiles("fileList");
        if (fileList.size() > 0) {
//遍历文件列表
            Iterator<MultipartFile> fileTte = fileList.iterator();
            while (fileTte.hasNext()) {
//获得每一个文件
                MultipartFile multipartFile = fileTte.next();
//获得原文件名
                String originalFilename2 = multipartFile.getOriginalFilename();
                originalFilename.append(originalFilename2);
                originalFilename.append(",");
                System.out.println("originalFilename:" + originalFilename);
                String realPath = request.getSession().getServletContext().getRealPath("/upload/renwu");
                File dir = new File(realPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filePath = realPath + "/" + originalFilename2;
                System.out.println("filePath:" + filePath);
//保存文件
                File dest = new File(filePath);
                if (!(dest.exists())) {
                    try {
                        multipartFile.transferTo(dest);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("_------------------------------------------------");
            }
        }
//        trainingtask.setTrainingnotice(originalFilename1);
        originalFilename.deleteCharAt(originalFilename.length() - 1);
//
//        trainingtask.setTrainingmaterials(originalFilename.toString());
//
//        trainingtaskDao.insert(trainingtask);

        return "fbpx";

    }


}
