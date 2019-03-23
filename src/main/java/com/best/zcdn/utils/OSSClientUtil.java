package com.best.zcdn.utils;

import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Knight on 2017/10/31.
 */
public class OSSClientUtil {


    private final static Logger LOGGER = LoggerFactory.getLogger(OSSClientUtil.class);

    private final static String KEY_OSS_END_POINT = "endpoint-oss";
    private final static String KEY_OSS_ACCESS_KEY = "accesskey-oss";
    private final static String KEY_OSS_ACCESS_SECRET = "accesssecret-oss";

    private static OSSClient ossClient;

    public static OSSClient getInstance() {
        if (ossClient == null) {
            synchronized (OSSClientUtil.class){
                if (ossClient == null) {
                    LOGGER.info("初始化ossClient");
//                    String endPoint = PropertyUtil.getProp(KEY_OSS_END_POINT);
                    String endPoint = "qwe";
//                    String accessKey = PropertyUtil.getProp(KEY_OSS_ACCESS_KEY);
                    String accessKey = "qwe";
                    String accessSecret = "ert";
//                    String accessSecret = PropertyUtil.getProp(KEY_OSS_ACCESS_SECRET);
                    if(endPoint != null&&accessKey != null&& accessSecret != null) {
                        ossClient = new OSSClient(endPoint,accessKey,accessSecret);
                    } else {
                        LOGGER.warn("初始化OSS入参缺失KEY_OSS_END_POINT :{}, KEY_OSS_ACCESS_KEY: {},KEY_OSS_ACCESS_SECRET: {}" ,
                                endPoint,accessKey, accessSecret );
                    }
                }
            }
        }
        return ossClient;
    }
}
