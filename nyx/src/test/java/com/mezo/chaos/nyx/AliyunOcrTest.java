package com.mezo.chaos.nyx;

import com.aliyun.com.viapi.FileUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.facebody.model.v20191230.DetectFaceRequest;
import com.aliyuncs.facebody.model.v20191230.DetectFaceResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeQrCodeRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeQrCodeResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeVerificationcodeRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeVerificationcodeResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.mezo.chaos.nyx.aliyun.AliyunConfig;
import com.mezo.chaos.nyx.aliyun.common.AliyunUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class AliyunOcrTest {


    @Test
    public void test01() {
        DefaultProfile profile = DefaultProfile.getProfile(AliyunConfig.REGION_ID, AliyunConfig.ACCESS_KEY_ID, AliyunConfig.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        DetectFaceRequest request = new DetectFaceRequest();
        request.setActionName(AliyunConfig.FUNCTION_DETECT_FACE);
        try {
            request.setImageURL(AliyunUtil.ossUploadFile("/Users/mezo/Downloads/sup-dog.png"));
            DetectFaceResponse response = client.getAcsResponse(request);
            response.getRequestId();
            System.out.println(new Gson().toJson(response));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

    }

    @Test
    public void test02() {
        DefaultProfile profile = DefaultProfile.getProfile(AliyunConfig.REGION_ID, AliyunConfig.ACCESS_KEY_ID, AliyunConfig.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        RecognizeVerificationcodeRequest request = new RecognizeVerificationcodeRequest();
        request.setRegionId("cn-shanghai");

        try {
            RecognizeVerificationcodeResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    @Test
    public void test03() {
        DefaultProfile profile = DefaultProfile.getProfile(AliyunConfig.REGION_ID, AliyunConfig.ACCESS_KEY_ID, AliyunConfig.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        RecognizeQrCodeRequest request = new RecognizeQrCodeRequest();
        request.setRegionId("cn-shanghai");
        try {
            ArrayList<RecognizeQrCodeRequest.Tasks> list = new ArrayList();
            RecognizeQrCodeRequest.Tasks  tasks= new RecognizeQrCodeRequest.Tasks();
            tasks.setImageURL(AliyunUtil.ossUploadFile("/Users/mezo/Downloads/sup-dog.png"));
            list.add(tasks);
            request.setTaskss(list);
            RecognizeQrCodeResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
