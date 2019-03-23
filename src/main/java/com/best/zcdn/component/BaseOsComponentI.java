package com.best.zcdn.component;

import com.best.zcdn.bean.OperationResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created by Knight on 2017/10/31.
 */
public interface BaseOsComponentI {

     OperationResult uploadFile(String dstPath, String localFilePath, String bucketName);

    /**
     * 上传文件
     * @param dstPath 上传oss文件路径
     * @param multipartFile 文件对象
     * @param bucketName
     * @param isPreload 是否需要预览
     * @return
     */
     OperationResult uploadFileCoverMultiPart(String dstPath, MultipartFile multipartFile, String bucketName, boolean isPreload);

    /**
     * 上传文件
     * @param dstPath 上传oss文件路径
     * @param inputStream 文件流
     * @param bucketName
     * @param isPreload 是否需要预览
     * @return
     */
    OperationResult uploadFileCoverInputStream(String dstPath, InputStream inputStream, String bucketName, boolean isPreload);

    /**
     * 强制上传文件(文件已存在直接覆盖)
     * @param dstPath 上传oss文件路径
     * @param inputStream 文件流
     * @param bucketName
     * @param isPreload 是否需要预览
     * @return
     */
    OperationResult uploadFileCoverInputStreamForce(String dstPath, InputStream inputStream, String bucketName, boolean isPreload);

    /**
     * 强制上传文件(文件已存在直接覆盖)
     * @param dstPath 上传oss文件路径
     * @param multipartFile 文件对象
     * @param bucketName
     * @param isPreload 是否需要预览
     * @return
     */
     OperationResult uploadFileCoverMultiPartForce(String dstPath, MultipartFile multipartFile, String bucketName, boolean isPreload);

    /**
     * 移动OSS文件
     * @param srcFilePath 源文件路径
     * @param dstFilePath 目标文件路径
     * @param bucketName
     * @return
     */
     OperationResult moveFileCover(String srcFilePath, String dstFilePath, String bucketName);
    /**
     * 强制移动OSS文件，覆盖
     * @param srcFilePath 源文件路径
     * @param dstFilePath 目标文件路径
     * @param bucketName
     * @return
     */
     OperationResult moveFileCoverForce(String srcFilePath, String dstFilePath, String bucketName);

    /**
     * 上传直接下载的文件
     * @param dstFilePath
     * @param contentBuffer
     * @param bucketName
     * @return
     */
     OperationResult uploadPredownloadFileBytes(String dstFilePath, byte[] contentBuffer, String bucketName);

    /**
     * 上传可预览的文件
     * @param dstFilePath
     * @param contentBuffer
     * @param bucketName
     * @return
     */
     OperationResult uploadPreviewFileBytes(String dstFilePath, byte[] contentBuffer, String bucketName);

     OperationResult downloadFile(String s, String s1, String s2) ;


     OperationResult updateContentDisposition(String s, String s1, String s2) ;

     OperationResult changeURLToPreRead(String s, String s1) ;

     OperationResult changeURLToPreDownLoad(String s, String s1) ;

     OperationResult delFile(String s, String s1) ;

     OperationResult createFolderPath(String s, String s1);

     OperationResult updateFolderPathAttr(String s, String s1, String s2);

     OperationResult getFolderAttr(String s, String s1);


     OperationResult delFolderPath(String s, String s1);


}
