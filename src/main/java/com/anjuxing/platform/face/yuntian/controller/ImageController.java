package com.anjuxing.platform.face.yuntian.controller;

import com.anjuxing.platform.face.yuntian.Service.ImageService;
import com.anjuxing.platform.face.yuntian.model.*;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping("/yuntian/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

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
    public ImageSearchResult imageSearch() throws IOException {
        return imageService.imageSearch();
    }

}
