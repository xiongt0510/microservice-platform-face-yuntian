package com.anjuxing.platform.face.yuntian.Service.impl;

import com.anjuxing.platform.face.yuntian.Service.LocalFaceService;
import com.anjuxing.platform.face.yuntian.Service.LocalTokenService;
import com.anjuxing.platform.face.yuntian.Service.RedisRepository;
import com.anjuxing.platform.face.yuntian.Service.remote.RemoteFaceService;
import com.anjuxing.platform.face.yuntian.model.FaceCompareParam;
import com.anjuxing.platform.face.yuntian.model.FaceCompareResult;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author xiongt
 * @Description
 */
@Service
public class LocalFaceServiceImpl implements LocalFaceService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final static int FACE_TYPE_NOT_IMPORTENT = 1;

    @Autowired
    private RemoteFaceService remoteFaceService;

    @Autowired
    private YuntianPropeties yt;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RedisRepository redis;

    @Autowired
    private LocalTokenService tokenService;



    @Override
    public FaceCompareResult loadFaceCompare(long faceIdA, long faceIdB) throws IOException {

        if (StringUtils.isEmpty(redis.getAccessToken())){
            tokenService.getLocalToken(yt.getClient());
        }

        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();

        String params = getFaceCompareJson(faceIdA,faceIdB);

        String data = remoteFaceService.faceImageCompare(params, authorization);

        logger.info("return remote data:" + data);

        return mapper.readValue(data,FaceCompareResult.class);
    }


    private String getFaceCompareJson(long faceIdA, long faceIdB) throws JsonProcessingException {
        FaceCompareParam compareParam = new FaceCompareParam();
        compareParam.setFaceIdA(faceIdA);
        compareParam.setFaceIdB(faceIdB);
        compareParam.setAtype(FACE_TYPE_NOT_IMPORTENT);
        compareParam.setBtype(FACE_TYPE_NOT_IMPORTENT);
        compareParam.setThreshold(yt.getFaceCompare().getThreshold());

       return mapper.writeValueAsString(compareParam);

    }


}
