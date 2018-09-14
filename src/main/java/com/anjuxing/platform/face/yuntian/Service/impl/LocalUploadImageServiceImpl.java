package com.anjuxing.platform.face.yuntian.Service.impl;

import com.anjuxing.platform.face.yuntian.Service.LocalFaceImageService;
import com.anjuxing.platform.face.yuntian.Service.LocalTokenService;
import com.anjuxing.platform.face.yuntian.Service.LocalUploadImageService;
import com.anjuxing.platform.face.yuntian.Service.RedisRepository;
import com.anjuxing.platform.face.yuntian.Service.remote.RemoteFaceImageService;
import com.anjuxing.platform.face.yuntian.Service.remote.RemoteUploadImageService;
import com.anjuxing.platform.face.yuntian.model.DataResult;
import com.anjuxing.platform.face.yuntian.model.UploadImageResult;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author xiongt
 * @Description
 */
@Service
public class LocalUploadImageServiceImpl implements LocalUploadImageService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RemoteUploadImageService remoteUploadImageService;

    @Autowired
    private RedisRepository redis;

    @Autowired
    private YuntianPropeties yt;

    @Autowired
    private LocalTokenService localTokenService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private LocalFaceImageService localFaceImageService;

    @Override
    public UploadImageResult getUploadImageResult(MultipartFile file , UploadType uploadType) throws IOException {

        //如果redis 中没有token 就重新获取并存储到redis 中
        if (StringUtils.isEmpty(redis.getAccessToken())){
            localTokenService.getLocalToken(yt.getClient());
        }

        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();


        String datas = remoteUploadImageService.imageUpload(uploadType.getValue(),file,authorization);

        logger.info("return remote data :" + datas);

        DataResult dataResult = mapper.readValue(datas,DataResult.class);

        UploadImageResult uploadImageResult = uploadImageResult(dataResult.getData());

        uploadImageResult.setSmallFaceImageResults(localFaceImageService.fetchSmallFaceImage(uploadImageResult.getId()));

        //调用小图像
        return uploadImageResult;
    }

    @Override
    public UploadImageResult getUploadImageResult(File file , UploadType uploadType) {
        return null;
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





}
