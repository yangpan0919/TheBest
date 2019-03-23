package com.best.zcdn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Knight on 2017/7/18.
 * For reading property file
 */
public class PropertyUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props = new Properties();
    private static final String FILE_NAME = "oss-config.properties";

    static {
        InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream(FILE_NAME);
        if (in != null) {

            try {
                props.load(in);
            } catch (IOException e) {
                LOGGER.error("读取配置文件{}失败",FILE_NAME,e);
            }
        } else {
            LOGGER.warn("缺失{},尝试从同路径下获取",FILE_NAME);
            File propFile = new File(FILE_NAME);
            try (FileInputStream inputStream = new FileInputStream(propFile)) {
                props.load(inputStream);
            } catch (IOException e) {
                LOGGER.error("读取配置文件{}失败,路径:{}",FILE_NAME, propFile.getAbsolutePath(),e);
            }
        }

    }
    public static String getProp(String param) {
        return props.getProperty(param);
    }

}
