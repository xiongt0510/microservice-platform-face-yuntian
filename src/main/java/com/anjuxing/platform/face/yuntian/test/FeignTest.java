package com.anjuxing.platform.face.yuntian.test;

import com.anjuxing.platform.face.yuntian.config.YunTianConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

/**
 * @author xiongt
 * @Description
 */
@FeignClient(name = "MICROSERVICE-PLATFORM-FACE-TENCENT",
        configuration = YunTianConfig.EncoderSupportConfig.class)
public interface FeignTest {

     @RequestMapping(value = "/object/store",method = RequestMethod.POST )
     String storeResult(@RequestParam("file") File file ,@RequestParam("key") String key);

     @RequestMapping(value = "/person/create/file",method = RequestMethod.POST,
     consumes = MediaType.APPLICATION_JSON_VALUE)
     String putPerson(@RequestBody Person person);

     @RequestMapping(value ="/face/verify" , method = RequestMethod.GET)
     String verify( @RequestParam("personId") String personId,@RequestParam("url") String url);
}
