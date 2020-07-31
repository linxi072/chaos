package com.mezo.chaos.nyx.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class AliyunConfig {

    public final static String ACCESS_KEY_ID = "LTAI4GKnKDeX5w7nRDMtHiDd";

    public final static String ACCESS_KEY_SECRET = "4kWf50zZkdIgipjb69edd8LYbsCaen";

    public final static String REGION_ID = "cn-shanghai";
    //身份证正面
    public final static String SIDE_FACE = "face";
    //身份证反面
    public final static String SIDE_BACK = "back";
    //身份证识别
    public final static String FUNCTION_RECOGNIZE_IDENTITY_CARD = "RecognizeIdentityCard";

    public final static String FUNCTION_DETECT_FACE = "DetectFace";

    public static IAcsClient initClient() {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        return new DefaultAcsClient(profile);
    }
}
