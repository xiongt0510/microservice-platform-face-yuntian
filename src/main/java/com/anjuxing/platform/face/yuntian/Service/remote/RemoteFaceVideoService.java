package com.anjuxing.platform.face.yuntian.Service.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author xiongt
 * @Description
 */
@FeignClient(name = "${yuntian.anjuxing.upload.name}", url = "${yuntian.anjuxing.upload.url}")
public interface RemoteFaceVideoService {

    @RequestMapping(method = RequestMethod.POST
             , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    String faceVideoCompare(
                            @RequestPart("fileVideo") MultipartFile video ,
//                            @RequestPart("fileImage") MultipartFile image,
//            @RequestPart MultiValueMap<String, MultipartFile> fileMap,
            @RequestParam("deviceId") int deviceId,
            @RequestHeader("Authorization")String authorization);
}
