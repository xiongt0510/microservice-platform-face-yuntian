package com.anjuxing.platform.face.yuntian.controller;

import com.anjuxing.platform.face.yuntian.Service.LocalUploadImageService;
import com.anjuxing.platform.face.yuntian.model.UploadImageResult;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping(YuntianConstanse.URL_UPLOAD_IMAGE)
public class UploadImageController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LocalUploadImageService localUploadImageService;

    @PostMapping
    public UploadImageResult uploadImage (@RequestParam("file") MultipartFile file) throws IOException {

        if (Objects.isNull(file)){
            logger.debug("上传的文件为null,请重新上传");
            throw new NullPointerException("上传的文件为null,请重新上传");
        }

        return localUploadImageService.getUploadImageResult(file,UploadType.RETRIEVE);
    }






}
