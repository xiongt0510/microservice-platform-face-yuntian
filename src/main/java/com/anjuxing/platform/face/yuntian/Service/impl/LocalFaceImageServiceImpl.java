package com.anjuxing.platform.face.yuntian.Service.impl;

import com.anjuxing.platform.face.yuntian.Service.LocalFaceImageService;
import com.anjuxing.platform.face.yuntian.Service.LocalTokenService;
import com.anjuxing.platform.face.yuntian.Service.RedisRepository;
import com.anjuxing.platform.face.yuntian.Service.remote.RemoteFaceImageService;
import com.anjuxing.platform.face.yuntian.model.SmallFaceImageResult;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xiongt
 * @Description
 */
@Service
public class LocalFaceImageServiceImpl implements LocalFaceImageService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private YuntianPropeties yt;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RedisRepository redis;

    @Autowired
    private LocalTokenService tokenService;

    @Autowired
    private RemoteFaceImageService remoteFaceImageService;



    @Override
    public List<SmallFaceImageResult> fetchSmallFaceImage(String bigImageId) {
        //如果redis 中没有token 就重新获取并存储到redis 中
        if (StringUtils.isEmpty(redis.getAccessToken())){
            tokenService.getLocalToken(yt.getClient());
        }

        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();

        String datas = remoteFaceImageService.faceImage(bigImageId,authorization);


        return mapperSmallFaceImageResult(datas);
    }


    private List<SmallFaceImageResult> mapperSmallFaceImageResult(String datas){

        List<SmallFaceImageResult> list = new ArrayList<>();

        try {
            JsonNode node = mapper.readTree(datas).path("data");
            Iterator<JsonNode> it = node.iterator();
            while (it.hasNext()){
                JsonNode nextNode = it.next();
               SmallFaceImageResult result =  mapper.convertValue(nextNode,SmallFaceImageResult.class);
               list.add(result);
            }

        } catch (Exception e) {
            throw new RuntimeException("获取 SmallFaceImageResult 数据失败");
        }
        return list;

    }
}
