package com.mezo.chaos.nyx;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.RpcAcsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.facebody.model.v20191230.CompareFaceRequest;
import com.aliyuncs.facebody.model.v20191230.CompareFaceResponse;
import com.aliyuncs.facebody.model.v20191230.DetectFaceRequest;
import com.aliyuncs.facebody.model.v20191230.DetectFaceResponse;
import com.aliyuncs.facebody.model.v20191230.RecognizeFaceRequest;
import com.aliyuncs.facebody.model.v20191230.RecognizeFaceResponse;
import com.aliyuncs.goodstech.model.v20191230.ClassifyCommodityRequest;
import com.aliyuncs.goodstech.model.v20191230.ClassifyCommodityResponse;
import com.aliyuncs.imageaudit.model.v20191230.ScanImageRequest;
import com.aliyuncs.imageaudit.model.v20191230.ScanImageRequest.Task;
import com.aliyuncs.imageaudit.model.v20191230.ScanImageResponse;
import com.aliyuncs.imageenhan.model.v20190930.ChangeImageSizeRequest;
import com.aliyuncs.imageenhan.model.v20190930.ChangeImageSizeResponse;
import com.aliyuncs.imageenhan.model.v20190930.ExtendImageStyleRequest;
import com.aliyuncs.imageenhan.model.v20190930.ExtendImageStyleResponse;
import com.aliyuncs.imageenhan.model.v20190930.MakeSuperResolutionImageRequest;
import com.aliyuncs.imageenhan.model.v20190930.MakeSuperResolutionImageResponse;
import com.aliyuncs.imageenhan.model.v20190930.RecolorImageRequest;
import com.aliyuncs.imageenhan.model.v20190930.RecolorImageRequest.ColorTemplate;
import com.aliyuncs.imageenhan.model.v20190930.RecolorImageResponse;
import com.aliyuncs.imagerecog.model.v20190930.DetectImageElementsRequest;
import com.aliyuncs.imagerecog.model.v20190930.DetectImageElementsResponse;
import com.aliyuncs.imagerecog.model.v20190930.RecognizeImageColorRequest;
import com.aliyuncs.imagerecog.model.v20190930.RecognizeImageColorResponse;
import com.aliyuncs.imagerecog.model.v20190930.RecognizeImageStyleRequest;
import com.aliyuncs.imagerecog.model.v20190930.RecognizeImageStyleResponse;
import com.aliyuncs.imagerecog.model.v20190930.RecognizeSceneRequest;
import com.aliyuncs.imagerecog.model.v20190930.RecognizeSceneResponse;
import com.aliyuncs.imagerecog.model.v20190930.TaggingImageRequest;
import com.aliyuncs.imagerecog.model.v20190930.TaggingImageResponse;
import com.aliyuncs.imageseg.model.v20191230.SegmentBodyRequest;
import com.aliyuncs.imageseg.model.v20191230.SegmentBodyResponse;
import com.aliyuncs.imageseg.model.v20191230.SegmentCommodityRequest;
import com.aliyuncs.imageseg.model.v20191230.SegmentCommodityResponse;
import com.aliyuncs.imageseg.model.v20191230.SegmentCommonImageRequest;
import com.aliyuncs.imageseg.model.v20191230.SegmentCommonImageResponse;
import com.aliyuncs.objectdet.model.v20191230.DetectMainBodyRequest;
import com.aliyuncs.objectdet.model.v20191230.DetectMainBodyResponse;
import com.aliyuncs.objectdet.model.v20191230.DetectVehicleRequest;
import com.aliyuncs.objectdet.model.v20191230.DetectVehicleResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeAccountPageRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeAccountPageResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeBankCardRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeBankCardResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeBusinessCardRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeBusinessCardResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeBusinessLicenseRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeBusinessLicenseResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeCharacterRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeCharacterResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeDriverLicenseRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeDriverLicenseResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeDrivingLicenseRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeDrivingLicenseResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeLicensePlateRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeLicensePlateResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeStampRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeStampResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeTableRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeTableResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeTaxiInvoiceRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeTaxiInvoiceResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeTrainTicketRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeTrainTicketResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeVINCodeRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeVINCodeResponse;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author benxiang.hhq
 */
public class ImageTest {

    static IAcsClient client = null;

    public static void main(String[] args) throws Exception {

        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-shanghai",             //默认
                "LTAI4GKnKDeX5w7nRDMtHiDd",         //您的AccessKeyID
                "4kWf50zZkdIgipjb69edd8LYbsCaen");    //您的AccessKeySecret

        client = new DefaultAcsClient(profile);

        // 图像增强
        testMakeSuperResolutionImage(); //超分辨率
        testChangeImageSize(); //尺寸变换
        testExtendImageStyle(); //风格迁移
        testRecolorImage(); //色彩迁移

        // 图像识别
        testDetectImageElements(); //元素检测
        testRecognizeImageColor(); //色板识别
        testRecognizeImageStyle(); //风格识别
        testRecognizeScene(); //场景识别
        testTaggingImage(); //通用图像打标


        // 人脸人体
        testDetectFace(); //人脸检测
        testRecognizeFace(); //人脸属性识别
        testCompareFace(); //人脸比对

        //图像裁剪
        testSegmentCommonImage(); // 图片裁剪
        testSegmentBody(); //人像分割
        testSegmentCommodity(); //商品分割

        // 目标检测
        testDetectMainBody(); //主体检测
        testDetectVehicle(); //机动车检测

        // 内容审核
        testScanImage(); // 内容审核

        // 商品理解
        testClassifyCommodity(); // 商品分类

        // 文字识别
        testRecognizeLicensePlate(); // 车牌识别
        testRecognizeBankCard(); //银行卡识别
        testRecognizeIdentityCard(); // 身份证识别
        testRecognizeTable(); //表格识别
        testRecognizeTrainTicket(); //火车票识别
        testRecognizeDriverLicense(); //驾驶证识别
        testRecognizeBusinessCard(); //名片识别
        testRecognizeTaxiInvoice(); //出租车发票识别
        testRecognizeVINCode(); //VIN码识别
        testRecognizeStamp(); //公章识别
        testRecognizeCharacter(); //通用识别
        testRecognizeBusinessLicense(); //营业执照识别
        testRecognizeAccountPage(); //户口页识别
        testRecognizeDrivingLicense(); //行驶证识别

        System.out.println("--------------------------------------------------------------");

    }

    private static <R extends RpcAcsRequest<T>, T extends AcsResponse> T getAcsResponse(R req) throws Exception {
        try {
            return client.getAcsResponse(req);
        } catch (ServerException e) {
            // 服务端异常
            System.out.println(String.format("ServerException: errCode=%s, errMsg=%s", e.getErrCode(), e.getErrMsg()));
            throw e;
        } catch (ClientException e) {
            // 客户端错误
            System.out.println(String.format("ClientException: errCode=%s, errMsg=%s", e.getErrCode(), e.getErrMsg()));
            throw e;
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            throw e;
        }
    }

    public static void testChangeImageSize() throws Exception {
        System.out.println("--------  尺寸变换 --------------");
        ChangeImageSizeRequest req = new ChangeImageSizeRequest();
        req.setUrl(
                "https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/ChangeImageSize/change-image-size-src.png");
        req.setWidth(800);
        req.setHeight(600);
        ChangeImageSizeResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testExtendImageStyle() throws Exception {
        ExtendImageStyleRequest req = new ExtendImageStyleRequest();
        System.out.println("--------  图像风格迁移 --------------");
        req.setStyleUrl(
                "http://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/ExtendImageStyle/majorUrl.jpeg");
        req.setMajorUrl(
                "http://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/ExtendImageStyle/styleUrl.jpeg");
        ExtendImageStyleResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testMakeSuperResolutionImage() throws Exception {
        MakeSuperResolutionImageRequest req = new MakeSuperResolutionImageRequest();
        System.out.print("--------  清晰化/超分辨 ----");
        req.setUrl(
                "http://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/MakeSuperResolution/sup-dog.png");
        MakeSuperResolutionImageResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testRecognizeImageColor() throws Exception {
        RecognizeImageColorRequest req = new RecognizeImageColorRequest();
        System.out.print("--------  色板识别 ----");
        req.setUrl(
                "http://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/RecognizeImageColor/RecognizeImageColor.png");
        RecognizeImageColorResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testRecognizeImageStyle() throws Exception {
        RecognizeImageStyleRequest req = new RecognizeImageStyleRequest();
        System.out.print("--------  风格识别 ----");
        req.setUrl(
                "http://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/RecognizeImageStyle/technology.png");
        RecognizeImageStyleResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testRecolorImage() throws Exception {
        RecolorImageRequest req = new RecolorImageRequest();
        System.out.print("--------  拓色 ----");
        req.setUrl(
                "http://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/RecolorImage/recolor-1-src.png");
        req.setColorCount(3);
        //（选填）拓色模式，默认AUTO，取值范围[AUTO：自动拓色, TEMPLATE：色板拓色, REF_PIC：参考图拓色]
        req.setMode("AUTO");

        RecolorImageResponse resp = null;
        //AUTO
        System.out.println("自动拓色");
        resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);

        //RefUrl TODO
        System.out.println("参考图拓色");
        req.setMode("REF_PIC");
        req.setRefUrl(
                "http://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/RecolorImage/recolor-refurl-src.png");
        resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);

        System.out.println("色板拓色");
        req.setMode("TEMPLATE");
        req.setUrl(
                "http://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/RecolorImage/recolor-2-src.png");
        List<ColorTemplate> colorTemplateList = new ArrayList<>();
        colorTemplateList.add(buildColorTemplate("056A6B"));
        colorTemplateList.add(buildColorTemplate("FF0000"));
        colorTemplateList.add(buildColorTemplate("00FF00"));
        req.setColorTemplates(colorTemplateList);
        resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static ColorTemplate buildColorTemplate(String color) {
        ColorTemplate colorTemplate = new ColorTemplate();
        colorTemplate.setColor(color);
        return colorTemplate;
    }

    public static void testDetectImageElements() throws Exception {
        DetectImageElementsRequest req = new DetectImageElementsRequest();
        System.out.print("--------  元素检测 -----");
        req.setUrl(
                "http://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/DetectImageElements/detect-elements-src.png");
        DetectImageElementsResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testDetectFace() throws Exception {
        System.out.println("--------  人脸检测定位 --------------");
        DetectFaceRequest req = new DetectFaceRequest();
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/SegmentCommonImage/segmentimage-src-hu.jpeg");
        DetectFaceResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testDetectMainBody() throws Exception {
        System.out.println("--------  主体检测 --------------");
        DetectMainBodyRequest req = new DetectMainBodyRequest();
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/SegmentCommonImage/segmentimage-src-hu.jpeg");
        DetectMainBodyResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testClassifyCommodity() throws Exception {
        System.out.println("--------  商品分类 --------------");
        ClassifyCommodityRequest req = new ClassifyCommodityRequest();
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/SegmentCommonImage/segmentimage-src-hu.jpeg");
        ClassifyCommodityResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testSegmentCommonImage() throws Exception {
        System.out.println("--------  图像裁剪 --------------");
        SegmentCommonImageRequest req = new SegmentCommonImageRequest();
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs"
                + ".com/viapi-demo/images/SegmentCommonImage/segmengImage.png");
        SegmentCommonImageResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testSegmentBody() throws Exception {
        System.out.println("--------  人像分割 --------------");
        SegmentBodyRequest req = new SegmentBodyRequest();
        req.setImageURL(
                "https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/SegmentBody/%E4%BA%BA%E5%83%8F%E5%88%86%E5%89%B2.png");
        SegmentBodyResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    //商品分割
    public static void testSegmentCommodity() throws Exception {
        System.out.println("--------  商品分割 --------------");
        SegmentCommodityRequest req = new SegmentCommodityRequest();
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/SegmentCommodity/%E5%95%86%E5%93%81.png");
        SegmentCommodityResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testRecognizeLicensePlate() throws Exception {
        System.out.println("--------  车牌识别 --------------");
        RecognizeLicensePlateRequest req = new RecognizeLicensePlateRequest();
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/RecognizeLicensePlate/licensePlate.jpg");
        RecognizeLicensePlateResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testRecognizeBankCard() throws Exception {
        System.out.println("--------  银行卡识别 --------------");
        RecognizeBankCardRequest req = new RecognizeBankCardRequest();
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/RecognizeBankCard/bankcard.jpg");
        RecognizeBankCardResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testRecognizeIdentityCard() throws Exception {
        System.out.println("--------  身份证识别 --------------");
        RecognizeIdentityCardRequest req = new RecognizeIdentityCardRequest();
        req.setSide("face");
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/RecognizeIdentityCard/identityCard.jpg");
        RecognizeIdentityCardResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void testScanImage() throws Exception {
        System.out.println("--------  内容审核 --------------");
        ScanImageRequest req = new ScanImageRequest();
        List<String> scenes = new ArrayList<String>();
        scenes.add("porn");
        req.setScenes(scenes);
        List<Task> tasks = new ArrayList<Task>();
        com.aliyuncs.imageaudit.model.v20191230.ScanImageRequest.Task task = new Task();
        task.setDataId(UUID.randomUUID().toString());
        task.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/ChangeImageSize/change-image-size-src.png");
        tasks.add(task);
        req.setTasks(tasks);

        ScanImageResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    //场景识别
    public static void testRecognizeScene() throws Exception {
        System.out.println("--------  场景识别 --------------");
        RecognizeSceneRequest req = new RecognizeSceneRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeSceneResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    //表格识别
    public static void testRecognizeTable() throws Exception {
        System.out.println("--------  表格识别 --------------");
        RecognizeTableRequest req = new RecognizeTableRequest();
        req.setUseFinanceModel(false);
        req.setAssureDirection(false);
        req.setHasLine(false);
        req.setSkipDetection(false);
        req.setOutputFormat("json");
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeTableResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //通用图像打标
    public static void testTaggingImage() throws Exception {
        System.out.println("--------  通用图像打标 --------------");
        TaggingImageRequest req = new TaggingImageRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        TaggingImageResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //机动车检测
    public static void testDetectVehicle() throws Exception {
        System.out.println("--------  机动车检测 --------------");
        DetectVehicleRequest req = new DetectVehicleRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        DetectVehicleResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //人脸属性识别
    public static void testRecognizeFace() throws Exception {
        System.out.println("--------  人脸属性识别 --------------");
        RecognizeFaceRequest req = new RecognizeFaceRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeFaceResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //火车票识别
    public static void testRecognizeTrainTicket() throws Exception {
        System.out.println("--------  火车票识别 --------------");
        RecognizeTrainTicketRequest req = new RecognizeTrainTicketRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeTrainTicketResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //驾驶证识别
    public static void testRecognizeDriverLicense() throws Exception {
        System.out.println("--------  驾驶证识别 --------------");
        RecognizeDriverLicenseRequest req = new RecognizeDriverLicenseRequest();
        req.setSide("face");
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeDriverLicenseResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //名片识别
    public static void testRecognizeBusinessCard() throws Exception {
        System.out.println("--------  名片识别 --------------");
        RecognizeBusinessCardRequest req = new RecognizeBusinessCardRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeBusinessCardResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //出租车发票识别
    public static void testRecognizeTaxiInvoice() throws Exception {
        System.out.println("--------  出租车发票识别 --------------");
        RecognizeTaxiInvoiceRequest req = new RecognizeTaxiInvoiceRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeTaxiInvoiceResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //VIN码识别
    public static void testRecognizeVINCode() throws Exception {
        System.out.println("--------  VIN码识别 --------------");
        RecognizeVINCodeRequest req = new RecognizeVINCodeRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeVINCodeResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //人脸比对
    public static void testCompareFace() throws Exception {
        System.out.println("--------  人脸比对 --------------");
        CompareFaceRequest req = new CompareFaceRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURLA("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        req.setImageURLB("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/yyy.png");

        CompareFaceResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //公章识别
    public static void testRecognizeStamp() throws Exception {
        System.out.println("--------  公章识别 --------------");
        RecognizeStampRequest req = new RecognizeStampRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeStampResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //通用识别
    public static void testRecognizeCharacter() throws Exception {
        System.out.println("--------  通用识别 --------------");
        RecognizeCharacterRequest req = new RecognizeCharacterRequest();
        req.setMinHeight(10);
        req.setOutputProbability(true);
        req.setImageURL(
                "https://viapi-demo.oss-cn-shanghai-internal.aliyuncs.com/viapi-demo/images/RecognizeCharacter/recognizeCharacter_demo.jpg");
        RecognizeCharacterResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //营业执照识别
    public static void testRecognizeBusinessLicense() throws Exception {
        System.out.println("--------  营业执照识别 --------------");
        RecognizeBusinessLicenseRequest req = new RecognizeBusinessLicenseRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeBusinessLicenseResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //户口页识别
    public static void testRecognizeAccountPage() throws Exception {
        System.out.println("--------  户口页识别 --------------");
        RecognizeAccountPageRequest req = new RecognizeAccountPageRequest();
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeAccountPageResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }
    //行驶证识别
    public static void testRecognizeDrivingLicense() throws Exception {
        System.out.println("--------  行驶证识别 --------------");
        RecognizeDrivingLicenseRequest req = new RecognizeDrivingLicenseRequest();
        req.setSide("face");
        // 注意：下面的链接换成自有的oss链接
        req.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/xxx.png");
        RecognizeDrivingLicenseResponse resp = getAcsResponse(req);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
    }

    public static void printResponse(String actionName, String requestId, AcsResponse  data) {
        System.out.println(String.format("actionName=%s, requestId=%s, data=%s", actionName, requestId,
                JSON.toJSONString(data) ));
    }
}


