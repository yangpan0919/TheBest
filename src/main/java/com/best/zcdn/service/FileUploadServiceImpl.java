package com.best.zcdn.service;


import com.best.zcdn.bean.FileUploadMessageDTO;
import com.best.zcdn.bean.OperationResult;
import com.best.zcdn.common.OssConstants;
import com.best.zcdn.component.BaseOsComponentI;
import com.best.zcdn.component.OssComponent;
import com.best.zcdn.constants.CSContents;
import com.best.zcdn.interfaces.FileUploadServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Created by YP on 2018/11/15.
 */
@Service
public class FileUploadServiceImpl implements FileUploadServiceI {


    private String bucketName = "csplatform-dev";
    private String aliyunDomainName =".oss-cn-hangzhou.aliyuncs.com/";

    private static BaseOsComponentI ossComponent = new OssComponent();

    private Logger logger = LoggerFactory.getLogger(getClass());



    /**
     * 生成随机文件名
     *
     * @param suffix
     * @return
     */
    @Override
    public String generateRandomFileName(String suffix) {
        //生成随机文件名
        String fileName = org.springframework.util.StringUtils.deleteAny(UUID.randomUUID().toString(), "-") + "." + suffix;
        return fileName;
    }
    /**
     *
     * @param multipartFile  文件
     * @param savePath   存储的中间路径值      activiti工作流文件存取路径： 流程定义的KEY(KEY)/流程实例ID(PROC_INST_ID)/任务实例ID(TASK_ID)/
     * @param flag      false-- 文件已存在时不覆盖源文件(生成随机文件名)   true--文件已存在时强制覆盖原文件（使用原有文件名）
     * @return
     */
    @Override
    public FileUploadMessageDTO uploadFile(MultipartFile multipartFile, String savePath, boolean flag) {
//        propertyConfig = new PropertyConfig();
//        propertyConfig.setBucketName("csplatform-dev");
//        propertyConfig.setAliyunDomainName(".oss-cn-hangzhou.aliyuncs.com/");

        //获得源文件名
        String srcName = multipartFile.getOriginalFilename();
        String fileName = null;
        if (flag) {
            fileName = srcName;
        } else {
            //获得文件后缀
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
            fileName = generateRandomFileName(suffix);
        }

        String cosFilePath = savePath + fileName;
        logger.info("开始调用上传接口");
        OperationResult operationResult = null;
        try {
            //上传文件,下载模式false
            if (flag) {
                //文件已存在强制覆盖
                operationResult = ossComponent.uploadFileCoverMultiPartForce(cosFilePath, multipartFile, bucketName, false);
            } else {
                operationResult = ossComponent.uploadFileCoverMultiPart(cosFilePath, multipartFile, bucketName, false);
            }
        } catch (Exception e) {
//            throw new ErrorCodeException(ErrorEnum.NOT_FIND_ERROR);
        }
        if (operationResult != null && !operationResult.getResultCode().equals(OssConstants.RESULT_SUCCESS)) {
            logger.warn("上传失败,errorCode:{},errorMessage:{}", operationResult.getResultCode(), operationResult.getResultMessage());
//            throw new ErrorCodeException(ThirdPartyErrorEnum.getErrorEnum(operationResult.getResultCode()));
        }
        //预览url
        String url = generateUploadUrl(cosFilePath);
        logger.info("调用上传接口成功");
        FileUploadMessageDTO fileUploadMessageDTO = new FileUploadMessageDTO();
        fileUploadMessageDTO.setUrl(url);
        fileUploadMessageDTO.setSrcName(srcName);
        return fileUploadMessageDTO;
    }

    private String generateUploadUrl(String cosFilePath) {
        String url = "http://" +bucketName + aliyunDomainName + cosFilePath;
        return url;
    }
}
