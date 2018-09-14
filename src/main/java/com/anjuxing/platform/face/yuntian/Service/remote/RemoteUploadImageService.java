package com.anjuxing.platform.face.yuntian.Service.remote;

import com.anjuxing.platform.face.yuntian.config.YunTianConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiongt
 * @Description  调用云天上传图片接口
 */
@FeignClient(name = "${yuntian.image.upload.name}", url = "${yuntian.image.upload.url}"
,configuration = YunTianConfig.EncoderSupportConfig.class)
public interface RemoteUploadImageService {

    @RequestMapping(value = "/true",method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     String imageUpload(
            @RequestParam("type") int type,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization")String authorization
    );



}
