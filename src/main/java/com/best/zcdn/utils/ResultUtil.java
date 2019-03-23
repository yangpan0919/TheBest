package com.best.zcdn.utils;


import com.best.zcdn.bean.OperationResult;
import com.best.zcdn.common.OssResultEnum;

/**
 * Created by Knight on 2017/7/18.
 * 调用结果拼装工具类，方便后期做结果转换
 */
public class ResultUtil {

    public static OperationResult createErrorResult(String errorCode,String errorMessage) {
        return new OperationResult(errorCode,errorMessage);
    }

    public static OperationResult createErrorResult(OssResultEnum errorEnum){
        return new OperationResult(errorEnum.getErrorCode(),errorEnum.getErrorCode());
    }

    public static OperationResult createSuccessResult() {
        return new OperationResult();
    }


}
