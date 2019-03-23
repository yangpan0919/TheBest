package com.best.zcdn.component;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.best.zcdn.bean.OperationResult;
import com.best.zcdn.common.OssResultEnum;
import com.best.zcdn.utils.OSSClientUtil;
import com.best.zcdn.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Knight on 2017/10/31.
 */
@Service
public class OssComponent implements BaseOsComponentI {

    private final static Logger LOGGER = LoggerFactory.getLogger(OssComponent.class);
    private final static String PREDOWNLOAD_COMPOSITION = "attachment";

    private OSSClient client = OSSClientUtil.getInstance();

    /**
     * 上传本地文件
     */
    @Override
    public OperationResult uploadFile(String dstPath, String localFilePath, String bucketName) {
        return null;
    }

    /**
     * 上传multipart文件
     *
     * @param dstFilePath   上传oss文件路径
     * @param multipartFile 文件对象
     * @param bucketName
     * @param isPreload     是否需要预览
     * @return
     */
    @Override
    public OperationResult uploadFileCoverMultiPart(String dstFilePath, MultipartFile multipartFile, String bucketName, boolean isPreload) {
        try {
            if (checkUploadParam(bucketName, dstFilePath)) {
                if (isPreload) {
                    client.putObject(bucketName, dstFilePath, multipartFile.getInputStream());
                } else {
                    ObjectMetadata meta = new ObjectMetadata();
                    meta.setContentDisposition(PREDOWNLOAD_COMPOSITION);
                    client.putObject(bucketName, dstFilePath, multipartFile.getInputStream(), meta);
                }
                return ResultUtil.createSuccessResult();
            } else {
                return ResultUtil.createErrorResult(OssResultEnum.ERROR_OSS_FILE_EXISTS);
            }
        } catch (OSSException se) {
            LOGGER.error("调用OSS服务端出现异常", se);
            return ResultUtil.createErrorResult(se.getErrorCode(), se.getErrorMessage());
        } catch (ClientException ce) {
            LOGGER.error("调用OSS服务端出现异常", ce);
            return ResultUtil.createErrorResult(ce.getErrorCode(), ce.getErrorMessage());
        } catch (IOException ioe) {
            LOGGER.error("获取文件输入流异常", ioe);
            return ResultUtil.createErrorResult(OssResultEnum.ERROR_OSS_FILE_GETSTREAM);
        }
    }

    /**
     * 上传文件
     * @param dstPath 上传oss文件路径
     * @param inputStream 文件流
     * @param bucketName
     * @param isPreload 是否需要预览
     * @return
     */
    @Override
    public OperationResult uploadFileCoverInputStream(String dstPath, InputStream inputStream, String bucketName, boolean isPreload) {
        try {
            if (checkUploadParam(bucketName, dstPath)) {
                if (isPreload) {
                    client.putObject(bucketName, dstPath, inputStream);
                } else {
                    ObjectMetadata meta = new ObjectMetadata();
                    meta.setContentDisposition(PREDOWNLOAD_COMPOSITION);
                    client.putObject(bucketName, dstPath, inputStream, meta);
                }
                return ResultUtil.createSuccessResult();
            } else {
                return ResultUtil.createErrorResult(OssResultEnum.ERROR_OSS_FILE_EXISTS);
            }
        } catch (OSSException se) {
            LOGGER.error("调用OSS服务端出现异常", se);
            return ResultUtil.createErrorResult(se.getErrorCode(), se.getErrorMessage());
        } catch (ClientException ce) {
            LOGGER.error("调用OSS服务端出现异常", ce);
            return ResultUtil.createErrorResult(ce.getErrorCode(), ce.getErrorMessage());
        }
    }

    /**
     * 强制上传文件(文件已存在直接覆盖)
     * @param dstPath 上传oss文件路径
     * @param inputStream 文件流
     * @param bucketName
     * @param isPreload 是否需要预览
     * @return
     */
    @Override
    public OperationResult uploadFileCoverInputStreamForce(String dstPath, InputStream inputStream, String bucketName, boolean isPreload) {
        try {
            if (isPreload) {
                client.putObject(bucketName, dstPath, inputStream);
            } else {
                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentDisposition(PREDOWNLOAD_COMPOSITION);
                client.putObject(bucketName, dstPath, inputStream, meta);
            }
            return ResultUtil.createSuccessResult();

        } catch (OSSException se) {
            LOGGER.error("调用OSS服务端出现异常", se);
            return ResultUtil.createErrorResult(se.getErrorCode(), se.getErrorMessage());
        } catch (ClientException ce) {
            LOGGER.error("调用OSS服务端出现异常", ce);
            return ResultUtil.createErrorResult(ce.getErrorCode(), ce.getErrorMessage());
        }
    }

    /**
     * 上传multipart文件
     *强制上传文件(文件已存在直接覆盖)
     * @param dstFilePath   上传oss文件路径
     * @param multipartFile 文件对象
     * @param bucketName
     * @param isPreload     是否需要预览
     * @return
     */
    @Override
    public OperationResult uploadFileCoverMultiPartForce(String dstFilePath, MultipartFile multipartFile, String bucketName, boolean isPreload) {
        try {
            if (isPreload) {
                client.putObject(bucketName, dstFilePath, multipartFile.getInputStream());
            } else {
                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentDisposition(PREDOWNLOAD_COMPOSITION);
                client.putObject(bucketName, dstFilePath, multipartFile.getInputStream(), meta);
            }
            return ResultUtil.createSuccessResult();

        } catch (OSSException se) {
            LOGGER.error("调用OSS服务端出现异常", se);
            return ResultUtil.createErrorResult(se.getErrorCode(), se.getErrorMessage());
        } catch (ClientException ce) {
            LOGGER.error("调用OSS服务端出现异常", ce);
            return ResultUtil.createErrorResult(ce.getErrorCode(), ce.getErrorMessage());
        } catch (IOException ioe) {
            LOGGER.error("获取文件输入流异常", ioe);
            return ResultUtil.createErrorResult(OssResultEnum.ERROR_OSS_FILE_GETSTREAM);
        }
    }
    /**
     * 移动OSS文件
     *
     * @param srcFilePath     源文件路径
     * @param destFilePath 目标文件路径
     * @param bucketName
     * @return
     */
    @Override
    public OperationResult moveFileCover(String srcFilePath, String destFilePath, String bucketName) {
        try {
            if (checkMoveFileParam(srcFilePath, destFilePath, bucketName)) {
                client.copyObject(bucketName, srcFilePath, bucketName, destFilePath);
                client.deleteObject(bucketName, srcFilePath);
                return ResultUtil.createSuccessResult();
            } else {
                LOGGER.info("oss移动文件，源文件：{}， 目标文件：{}， bucket {}不满足条件", srcFilePath, destFilePath, bucketName);
                return ResultUtil.createErrorResult(OssResultEnum.ERROR_OSS_FILE_MOVE_CRITIRA);
            }
        } catch (OSSException se) {
            LOGGER.error("调用OSS服务端出现异常", se);
            return ResultUtil.createErrorResult(se.getErrorCode(), se.getErrorMessage());
        } catch (ClientException ce) {
            LOGGER.error("调用OSS服务端出现异常", ce);
            return ResultUtil.createErrorResult(ce.getErrorCode(), ce.getErrorMessage());
        }
    }
    /**
     * 强制移动OSS文件，覆盖
     *
     * @param srcFilePath     源文件路径
     * @param destFilePath 目标文件路径
     * @param bucketName
     * @return
     */
    @Override
    public OperationResult moveFileCoverForce(String srcFilePath, String destFilePath, String bucketName) {
        try {
            if (client.doesObjectExist(bucketName, srcFilePath)) {
                if(client.doesObjectExist(bucketName, destFilePath)){
                    client.deleteObject(bucketName, destFilePath);
                }
                client.copyObject(bucketName, srcFilePath, bucketName, destFilePath);
                client.deleteObject(bucketName, srcFilePath);
                return ResultUtil.createSuccessResult();
            } else {
                LOGGER.info("oss移动文件，源文件：{}， 目标文件：{}， bucket {}不满足条件", srcFilePath, destFilePath, bucketName);
                return ResultUtil.createErrorResult(OssResultEnum.ERROR_OSS_FILE_MOVE_CRITIRA);
            }
        } catch (OSSException se) {
            LOGGER.error("调用OSS服务端出现异常", se);
            return ResultUtil.createErrorResult(se.getErrorCode(), se.getErrorMessage());
        } catch (ClientException ce) {
            LOGGER.error("调用OSS服务端出现异常", ce);
            return ResultUtil.createErrorResult(ce.getErrorCode(), ce.getErrorMessage());
        }
    }

    /**
     * 上传直接下载的文件
     *
     * @param dstFilePath
     * @param contentBuffer
     * @param bucketName
     * @return
     */
    @Override
    public OperationResult uploadPredownloadFileBytes(String dstFilePath, byte[] contentBuffer, String bucketName) {
        try {
            if(checkUploadParam(bucketName,dstFilePath)){
                uploadFileBytes(dstFilePath,contentBuffer,bucketName,false);
                return ResultUtil.createSuccessResult();
            } else {
                LOGGER.info("oss上传文件，源文件：{}，bucket {}已存在",dstFilePath,bucketName);
                return ResultUtil.createErrorResult(OssResultEnum.ERROR_OSS_FILE_EXISTS);
            }
        }catch (OSSException se) {
            LOGGER.error("调用OSS服务端出现异常",se);
            return ResultUtil.createErrorResult(se.getErrorCode(),se.getErrorMessage());
        }catch (ClientException ce) {
            LOGGER.error("调用OSS服务端出现异常",ce);
            return ResultUtil.createErrorResult(ce.getErrorCode(),ce.getErrorMessage());
        }
    }

    /**
     * 上传可预览的文件
     *
     * @param dstFilePath
     * @param contentBuffer
     * @param bucketName
     * @return
     */
    @Override
    public OperationResult uploadPreviewFileBytes(String dstFilePath, byte[] contentBuffer, String bucketName) {
        if(checkUploadParam(bucketName, dstFilePath)){
            //默认为preview设置
            uploadFileBytes(dstFilePath,contentBuffer,bucketName,true);
            return  ResultUtil.createSuccessResult();
        } else {
            LOGGER.info("oss上传文件，源文件：{}，bucket {}已存在", dstFilePath,bucketName);
            return  ResultUtil.createErrorResult(OssResultEnum.ERROR_OSS_FILE_EXISTS);
        }
    }

    @Override
    public OperationResult downloadFile(String s, String s1, String s2) {
        return null;
    }


    @Override
    public OperationResult updateContentDisposition(String s, String s1, String s2) {
        return null;
    }

    @Override
    public OperationResult changeURLToPreRead(String s, String s1) {
        return null;
    }

    @Override
    public OperationResult changeURLToPreDownLoad(String s, String s1) {
        return null;
    }

    @Override
    public OperationResult delFile(String s, String s1) {
        return null;
    }

    @Override
    public OperationResult createFolderPath(String s, String s1) {
        return null;
    }

    @Override
    public OperationResult updateFolderPathAttr(String s, String s1, String s2) {
        return null;
    }

    @Override
    public OperationResult getFolderAttr(String s, String s1) {
        return null;
    }

    @Override
    public OperationResult delFolderPath(String s, String s1) {
        return null;
    }

    /**
     * 移动文件时校验源文件和目标文件
     *
     * @param bucketName
     * @param cosFilePath
     * @return
     */
    private boolean checkMoveFileParam(String cosFilePath, String destCOSFilePath, String bucketName) {
        //源文件必须存在且目标文件不存在
        if (client.doesObjectExist(bucketName, cosFilePath)
                && !client.doesObjectExist(bucketName, destCOSFilePath)) {
            return true;
        }
        return false;
    }

    /**
     * 上传前校验判断文件是否已存在
     *
     * @param bucketName
     * @param cosFilePath
     * @return
     */
    private boolean checkUploadParam(String bucketName, String cosFilePath) {
        if (client.doesObjectExist(bucketName, cosFilePath)) {
            return false;
        }
        return true;
    }

    private void uploadFileBytes(String dstFilePath, byte[] contentBuffer, String bucketName, boolean isPreload) {
        if (isPreload) {
            uploadFileBytes(dstFilePath,contentBuffer,bucketName,null);
        } else {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentDisposition(PREDOWNLOAD_COMPOSITION);
            uploadFileBytes(dstFilePath,contentBuffer,bucketName,meta);
        }
    }

    private void uploadFileBytes(String cosFilePath, byte[] contentBuffer, String bucketName, ObjectMetadata meta) {
        if (meta != null) {
            client.putObject(bucketName, cosFilePath, new ByteArrayInputStream(contentBuffer), meta);
        } else {
            client.putObject(bucketName, cosFilePath, new ByteArrayInputStream(contentBuffer));
        }
    }
}
