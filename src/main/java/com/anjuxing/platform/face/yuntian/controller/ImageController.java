package com.anjuxing.platform.face.yuntian.controller;

import com.anjuxing.platform.face.yuntian.Service.ImageService;
import com.anjuxing.platform.face.yuntian.model.*;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping("/yuntian/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/upload/file")
    public UploadImageResult uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return imageService.uploadImage(file, UploadType.RETRIEVE);
    }

    @PostMapping("/compare")
    public ImageCompareResult imageCompare(@RequestParam("faceIdA") long faceIdA,
                                           @RequestParam("faceIdB") long faceIdB) throws IOException {
        return imageService.imagesCompare(faceIdA, faceIdB);
    }

    @GetMapping("/fetch/small")
    public List<SmallImageResult> smallImages(@RequestParam("id") String bigImageId) throws IOException {
        return imageService.fetchSmallImage(bigImageId);
    }

    @PostMapping(value = "/video/compare")
    public ImageVideoCompareResult imageVideoCompare(
            @RequestParam("fileVideo") MultipartFile fileVideo,
            @RequestParam("fileImage") MultipartFile fileImage,
            @RequestParam("deviceId") int deviceId
    ) throws Exception {

        return imageService.imageVideoCompare(fileVideo,fileImage,deviceId);

    }

    @PostMapping(value = "/search")
    public String imageSearch(
            @RequestParam("communityId") String communityId ,
            @RequestParam("threshold") String threshold,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("size") int size) throws IOException {

        String result  = imageService.imageSearch(communityId,threshold,imageUrl,size);

//        ImageSearchResult imageSearchResult =  objectMapper.readValue(objectMapper.readTree(result).path("data").toString(),ImageSearchResult.class);
//
//        if (Objects.isNull(imageSearchResult)){
//            imageSearchResult
//        }
        return result;
    }

}
