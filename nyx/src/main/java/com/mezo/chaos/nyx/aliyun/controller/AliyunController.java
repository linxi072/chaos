package com.mezo.chaos.nyx.aliyun.controller;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.google.gson.Gson;
import com.aliyuncs.ocr.model.v20191230.*;
import com.mezo.chaos.nyx.aliyun.AliyunConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mezo
 */
@RequestMapping("aliyun")
@RestController
public class AliyunController {


    @RequestMapping(value = "/idCard",method = RequestMethod.POST)
    public void RecognizeIdentityCard(@RequestParam("side")String side,@RequestParam("imgStr")String imgStr) {
        try {

            IAcsClient client = AliyunConfig.initClient();
            RecognizeIdentityCardRequest request = new RecognizeIdentityCardRequest();
            request.setRegionId("cn-shanghai");
            request.setActionName(AliyunConfig.FUNCTION_RECOGNIZE_IDENTITY_CARD);
            request.setSide(side);
            request.setImageURL(imgStr);
            RecognizeIdentityCardResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}

