package com.best.zcdn.common;

/**
 * Created by Knight on 2017/7/18.
 */
public enum OssResultEnum {

    COS_SUCCESS("01","成功"),
    ERROR_OSS_FILE_NOT_EXISTS("0001","文件不存在"),
    ERROR_OSS_FILE_EXISTS("0002","文件已存在"),
    ERROR_OSS_FILE_MOVE_CRITIRA("0003","源文件不存在或目标文件已存在"),
    ERROR_OSS_FILE_GETSTREAM("0010","获取文件流异常"),
    COS_SERVICE_ERRPR("-2", "调用腾讯云COS接口失败"),
    BUCKETNAME_NULL("1000", "Bucket名称不能为空"),
    COSPATH_NULL("1001", "cos路径不能为空"),
    COSPATH_INVALID_FORMATE("1002", "cos路径格式不正确"),
    LOCALPATH_NOT_EXISTS("1003", "待上传文件不存在"),
    EXT_NOT_EQUAL("1004", "本地保存文件扩展名与服务器文件不一致"),
    FILE_EXT_ERROR("1005", "文件扩展名错误"),
    LOCALPATH_INVALID_FORMATE("1006", "本地文件路径格式不正确"),
    LOCALPATH_NULL("1007", "本地文件路径不能为空"),
    READ_LOCALFILE_ERROR("1008", "读取文件失败"),
    READ_STREAM_ERROR("1009", "读取输入流数据失败"),
    SAVE_FILE_ERROR("1010", "保存临时文件失败"),
    HTTP_BAD_REQUEST("400", "请求不合法，包体格式错误"),
    ERROR_CMD_COS_ERROR("-181", "存储后端错误"),
    ERROR_CMD_COS_INVALID_PATH("-180", "非法路径"),
    ERROR_CMD_COS_INVALID_MOD_FLAG("-179", "修改标志非法"),
    ERROR_CMD_COS_PATH_CONFLICT("-178", "路径冲突"),
    ERROR_CMD_COS_FILE_EXIST("-177", "文件已存在"),
    ERROR_CMD_COS_BUCKET_EXIST("-176", "Bucket已存在"),
    ERROR_CMD_COS_FILE_SIZE_NOT_EQU("-175", "文件大小不一致"),
    ERROR_CMD_COS_SHA_NOT_EQU("-174", "文件SHA不一致"),
    ERROR_CMD_COS_DIR_NOT_EMPTY("-173", "目录非空"),
    ERROR_CMD_COS_BUCKET_NUM_LIMIT("-172", "Bucket数量限制"),
    ERROR_CMD_COS_INVALID_APP_TYPE("-171", "AppType非法"),
    ERROR_CMD_COS_INVALID_LIST_NUM("-170", "LIST数量非法"),
    ERROR_CMD_COS_INVALID_QUERY_FLAG("-169", "查询标记非法"),
    ERROR_CMD_COS_INVALID_SHA("-168", "非法SHA"),
    ERROR_CMD_COS_INVALID_CNAME("-167", "非法的CNAME"),
    ERROR_CMD_COS_INDEX_ERROR("-166", "索引不存在"),
    ERROR_CMD_COS_MAX_NUM("-165", "单次拉取目录最大支持199");

    String errorCode;
    String errorMessage;
    OssResultEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
