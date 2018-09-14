package com.anjuxing.platform.face.yuntian.Service.impl;

import com.anjuxing.platform.face.yuntian.Service.LocalFaceVideoService;
import com.anjuxing.platform.face.yuntian.Service.LocalTokenService;
import com.anjuxing.platform.face.yuntian.Service.RedisRepository;
import com.anjuxing.platform.face.yuntian.Service.remote.RemoteFaceVideoService;
import com.anjuxing.platform.face.yuntian.model.FaceVideoResult;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
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

import java.io.IOException;
import java.util.*;


/**
 * @author xiongt
 * @Description
 */
@Service
public class LocalFaceVideoServiceImpl implements LocalFaceVideoService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RemoteFaceVideoService remoteFaceVideoService;

    @Autowired
    private RedisRepository redis;

    @Autowired
    private LocalTokenService localTokenService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private YuntianPropeties yt;

    @Override
    public FaceVideoResult getFaceVideoResult(MultipartFile fileVideo, MultipartFile fileImage, int deviceId) throws Exception {
        //如果redis 中没有token 就重新获取并存储到redis 中
        if (StringUtils.isEmpty(redis.getAccessToken())){
            localTokenService.getLocalToken(yt.getClient());
        }

        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();


        String url = "http://190.35.194.192:8083/api/intellif/anjuxing/upload";
        HttpResponse httpResponse = remoteFaceVideoResult(url,fileVideo,fileImage,deviceId,authorization);

        String datas =  EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
        logger.info("return remote data :" + datas);

        return faceVideoResult(datas);
    }

    private FaceVideoResult faceVideoResult(String datas) throws IOException {

        JsonNode dataNode = mapper.readTree(datas).path("data");
        FaceVideoResult result = new FaceVideoResult();

        result.setState(dataNode.path("state").asText());
        result.setMsg(dataNode.path("msg").asText());

        JsonNode imageDataNode = dataNode.path("imgData");
        Iterator it = imageDataNode.iterator();
        while (it.hasNext()){
            FaceVideoResult.ImageData imageData = mapper.convertValue(it.next(),FaceVideoResult.ImageData.class);
            if (Objects.isNull(result.getImageData())){
                result.setImageData(new ArrayList());
            }
            result.getImageData().add(imageData);
        }

        return result;



    }

    private HttpResponse remoteFaceVideoResult(String url,MultipartFile fileVideo, MultipartFile fileImage, int deviceId,
                                               String authorization) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader("Content-Type","multipart/form-data");
        httpPost.addHeader("Authorization",authorization);


        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.setCharset(java.nio.charset.Charset.forName("UTF-8"));
        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        String fileVideoName = fileVideo.getOriginalFilename();
        String fileImageName = fileImage.getOriginalFilename();
        reqEntity.addBinaryBody("fileVideo", fileVideo.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileVideoName);// 文件流
        reqEntity.addBinaryBody("fileImage", fileImage.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileImageName);// 文件流
        reqEntity.addTextBody("deviceId", String.valueOf(deviceId));

        HttpEntity entity = reqEntity.build();
        httpPost.setEntity(entity);
        HttpResponse response = httpclient.execute(httpPost);// 执行提交

        return response;
    }
}
