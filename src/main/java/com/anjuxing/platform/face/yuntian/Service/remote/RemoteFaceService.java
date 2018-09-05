package com.anjuxing.platform.face.yuntian.Service.remote;

import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiongt
 * @Description
 */
@FeignClient(name = "${yuntian.face.name}", url = "${yuntian.face.url}")
public interface RemoteFaceService {

    @RequestMapping(value = YuntianConstanse.URL_FACE_COMPARE,
            method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    String faceImageCompare(
            String params,
      @RequestHeader("Authorization")String authorization
    );



}
