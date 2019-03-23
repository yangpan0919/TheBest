package com.best.zcdn.interfaces;


import com.best.zcdn.bean.FileUploadMessageDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by YP on 2018/11/15.
 */
public interface FileUploadServiceI {

    String generateRandomFileName(String suffix);

    FileUploadMessageDTO uploadFile(MultipartFile multipartFile, String savePath, boolean flag);


}
