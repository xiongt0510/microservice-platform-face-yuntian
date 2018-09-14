package com.anjuxing.platform.face.yuntian.controller;

import com.anjuxing.platform.face.yuntian.Service.LocalFaceImageService;
import com.anjuxing.platform.face.yuntian.Service.LocalFaceService;
import com.anjuxing.platform.face.yuntian.model.FaceCompareResult;
import com.anjuxing.platform.face.yuntian.model.SmallFaceImageResult;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    private LocalFaceImageService localFaceImageService;




    @PostMapping(YuntianConstanse.URL_FACE_COMPARE)
    public FaceCompareResult faceCompare(@RequestParam("faceIdA") long faceIdA ,
                                         @RequestParam("faceIdB") long faceIdB) throws IOException {

       return localFaceService.loadFaceCompare(faceIdA,faceIdB);

    }

    @GetMapping("/small/image")
    public List<SmallFaceImageResult> smallFaceImageResults(@RequestParam("id") String bigImageId){

       return localFaceImageService.fetchSmallFaceImage(bigImageId);

    }

}
