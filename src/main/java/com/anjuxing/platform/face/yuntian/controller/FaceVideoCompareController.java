package com.anjuxing.platform.face.yuntian.controller;

import com.anjuxing.platform.face.yuntian.Service.LocalFaceVideoService;
import com.anjuxing.platform.face.yuntian.model.FaceVideoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping("/face/video")
public class FaceVideoCompareController {

    @Autowired
    private LocalFaceVideoService localFaceVideoService;



    @RequestMapping(method = RequestMethod.POST,value = "/compare")
    public FaceVideoResult faceVideoResult(
           @RequestParam("fileVideo") MultipartFile fileVideo,
           @RequestParam("fileImage") MultipartFile fileImage,
           @RequestParam("deviceId") int deviceId
    ) throws Exception {

        return localFaceVideoService.getFaceVideoResult(fileVideo,fileImage,deviceId);

    }



}
