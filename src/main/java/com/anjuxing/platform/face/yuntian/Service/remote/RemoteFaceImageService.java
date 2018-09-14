package com.anjuxing.platform.face.yuntian.Service.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xiongt
 * @Description  获取上传图片后的小图像
 */
@FeignClient(name = "${yuntian.face.image.name}", url = "${yuntian.face.image.url}")
public interface RemoteFaceImageService {

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    String faceImage(@PathVariable("id") String id,
                     @RequestHeader("Authorization")String authorization);
}
