package com.mezo.chaos.nyx.aliyun.common;

import com.aliyun.com.viapi.FileUtils;
import com.aliyuncs.exceptions.ClientException;
import com.mezo.chaos.nyx.aliyun.AliyunConfig;

import java.io.IOException;

/**
 *
 * @author mezo
 */
public class AliyunUtil {

    public static String ossUploadFile(String filePath) throws ClientException, IOException {
        FileUtils fileUtils = FileUtils.getInstance(AliyunConfig.ACCESS_KEY_ID, AliyunConfig.ACCESS_KEY_SECRET);
        return fileUtils.upload(filePath);
    }
}
