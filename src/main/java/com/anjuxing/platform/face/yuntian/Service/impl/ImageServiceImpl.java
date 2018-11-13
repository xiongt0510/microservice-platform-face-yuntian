package com.anjuxing.platform.face.yuntian.Service.impl;

import com.anjuxing.platform.face.yuntian.Service.ImageService;
import com.anjuxing.platform.face.yuntian.Service.RedisRepository;
import com.anjuxing.platform.face.yuntian.Service.RemoteService;
import com.anjuxing.platform.face.yuntian.Service.TokenService;
import com.anjuxing.platform.face.yuntian.model.*;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import com.anjuxing.platform.face.yuntian.util.HttpClientUtils;
import com.anjuxing.platform.face.yuntian.util.ImageUtil;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.StringValueExp;
import java.io.IOException;
import java.util.*;

/**
 * @author xiongt
 * @Description
 */
@Service
public class ImageServiceImpl implements ImageService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisRepository redis;

    @Autowired
    private YuntianPropeties yt;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RemoteService remoteService;

    private final static int FACE_TYPE_NOT_IMPORTENT = 1;


    @Override
    public UploadImageResult uploadImage(MultipartFile file, UploadType uploadType) throws IOException {
        //如果redis 中没有token 就重新获取并存储到redis 中
        if (StringUtils.isEmpty(redis.getAccessToken())){
            tokenService.getToken(yt.getClient());
        }

        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();


        String datas = remoteService.imageUpload(uploadType.getValue(),file,authorization);

        logger.info("return remote data :" + datas);

        DataResult dataResult = mapper.readValue(datas,DataResult.class);

        UploadImageResult uploadImageResult = uploadImageResult(dataResult.getData());

        uploadImageResult.setSmallFaceImageResults(fetchSmallImage(uploadImageResult.getId()));

        //调用小图像
        return uploadImageResult;
    }

    private UploadImageResult uploadImageResult(Object data){
        UploadImageResult uploadImage = null;
        if (Objects.nonNull(data)){
            uploadImage = mapper.convertValue(data,UploadImageResult.class);
        } else {
            uploadImage = new UploadImageResult();
        }

        return uploadImage;

    }

    @Override
    public List<SmallImageResult> fetchSmallImage(String bigImageId) throws IOException {

        //如果redis 中没有token 就重新获取并存储到redis 中
        if (StringUtils.isEmpty(redis.getAccessToken())){
            tokenService.getToken(yt.getClient());
        }

        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();

        String datas = remoteService.searchImages(bigImageId,authorization);

        return mapperSmallFaceImageResult(datas);
    }

    private List<SmallImageResult> mapperSmallFaceImageResult(String datas){

        List<SmallImageResult> list = new ArrayList<>();

        try {
            JsonNode node = mapper.readTree(datas).path("data");
            Iterator<JsonNode> it = node.iterator();
            while (it.hasNext()){
                JsonNode nextNode = it.next();
                SmallImageResult result =  mapper.convertValue(nextNode,SmallImageResult.class);
                list.add(result);
            }

        } catch (Exception e) {
            throw new RuntimeException("获取 SmallFaceImageResult 数据失败");
        }
        return list;
    }

    @Override
    public ImageCompareResult imagesCompare(long faceIdA, long faceIdB) throws IOException {
        if (StringUtils.isEmpty(redis.getAccessToken())){
            tokenService.getToken(yt.getClient());
        }

        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();

        String params = getFaceCompareJson(faceIdA,faceIdB);

        String url = "http://190.35.194.192:8083/api/intellif/face/compare";

//        String data = remoteService.faceImageCompare(params, authorization);

        String data = HttpClientUtils.callRemote4JsonString(url,authorization,params);

        logger.info("return remote data:" + data);

        return mapper.readValue(data,ImageCompareResult.class);
    }

    private String getFaceCompareJson(long faceIdA, long faceIdB) throws JsonProcessingException {
        ImageCompareParam compareParam = new ImageCompareParam();
        compareParam.setFaceIdA(faceIdA);
        compareParam.setFaceIdB(faceIdB);
        compareParam.setAtype(FACE_TYPE_NOT_IMPORTENT);
        compareParam.setBtype(FACE_TYPE_NOT_IMPORTENT);
        compareParam.setThreshold(yt.getFaceCompare().getThreshold());

        return mapper.writeValueAsString(compareParam);

    }

    @Override
    public ImageVideoCompareResult imageVideoCompare(MultipartFile fileVideo, MultipartFile fileImage, int deviceId) throws Exception {
        //如果redis 中没有token 就重新获取并存储到redis 中
        if (StringUtils.isEmpty(redis.getAccessToken())){
            tokenService.getToken(yt.getClient());
        }
        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();

        String url = "http://190.35.194.192:8083/api/intellif/anjuxing/upload";
        Map<String,MultipartFile> fileMap = new HashMap<>();
        fileMap.put("fileVideo",fileVideo);
        fileMap.put("fileImage",fileImage);

        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("deviceId", String.valueOf(deviceId));

        String datas = HttpClientUtils.callRemote4Multipart(url,authorization,fileMap,paramsMap);

        return imageVideoResult(datas);
    }

    private ImageVideoCompareResult imageVideoResult(String datas) throws IOException {

        JsonNode dataNode = mapper.readTree(datas).path("data");
        ImageVideoCompareResult result = new ImageVideoCompareResult();

        result.setState(dataNode.path("state").asText());
        result.setMsg(dataNode.path("msg").asText());

        JsonNode imageDataNode = dataNode.path("imgData");
        Iterator it = imageDataNode.iterator();
        while (it.hasNext()){
            ImageVideoCompareResult.ImageData imageData = mapper.convertValue(it.next(),ImageVideoCompareResult.ImageData.class);
            if (Objects.isNull(result.getImageData())){
                result.setImageData(new ArrayList());
            }
            result.getImageData().add(imageData);
        }

        return result;
    }


    /**
     *
     * @param communityId "ca9a6d59-cd7c-4444-b16a-7584d851528b"
     * @param threshold "0.92"
     * @param imageUrl ImageUtil.getImageUrlFromPath("G:\\864707704277677402.jpg")
     * @param size 1
     * @return
     * @throws IOException
     */
    @Override
    public String imageSearch(String communityId,
                                         String threshold,String imageUrl,int size) throws IOException {

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("labels","1");
        paramMap.put("communityId",communityId);
        paramMap.put("threshold",threshold);
        paramMap.put("base64pic",ImageUtil.getImageStrFromUrl(imageUrl) );
        paramMap.put("urlpic","1");
        paramMap.put("key","8192302-sd9230043-3232122");
        paramMap.put("size",size);

        String url = "http://190.35.194.198:8063/api/intellif/mining/analysis/searchpeopleinfo";

        //如果redis 中没有token 就重新获取并存储到redis 中
        if (StringUtils.isEmpty(redis.getAccessToken())){
            tokenService.getToken(yt.getClient());
        }

        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();
        String jsonParam = mapper.writeValueAsString(paramMap);
        String result = HttpClientUtils.callRemote4JsonString(url,authorization,jsonParam);
        logger.info(result);


        return result;
    }



}
