package com.anjuxing.platform.face.yuntian.controller;

import com.anjuxing.platform.face.yuntian.Service.LocalFaceService;
import com.anjuxing.platform.face.yuntian.Service.remote.RemoteFaceService;
import com.anjuxing.platform.face.yuntian.Service.RedisRepository;
import com.anjuxing.platform.face.yuntian.model.FaceCompareParam;
import com.anjuxing.platform.face.yuntian.model.FaceCompareResult;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping(YuntianConstanse.URL_FACE)
public class FaceCompareController {


    @Autowired
    private LocalFaceService localFaceService;



    @Autowired
    private RedisRepository redis;

    @PostMapping(YuntianConstanse.URL_FACE_COMPARE)
    public FaceCompareResult faceCompare(@RequestParam("faceIdA") long faceIdA ,
                                         @RequestParam("faceIdB") long faceIdB) throws IOException {

       return localFaceService.loadFaceCompare(faceIdA,faceIdB);

    }

}
