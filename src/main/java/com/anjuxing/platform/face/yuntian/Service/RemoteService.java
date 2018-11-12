package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.config.YunTianConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiongt
 * @Description
 */
@FeignClient(name = "${yuntian.service.name}",
        configuration = YunTianConfig.EncoderSupportConfig.class)
public interface RemoteService {

    /**
     * 获取token
     * @param grantType
     * @param username
     * @param password
     * @param scope
     * @param clientId
     * @param clientSecret
     * @param authorization
     * @return
     */
    @PostMapping(value = "${yuntian.token.url}"  ,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String getToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            @RequestParam("scope")String scope,
            @RequestParam("client_id")String clientId,
            @RequestParam("client_secret")String clientSecret,
            @RequestHeader("Authorization")String authorization
    );


    /**
     * 上传图片接口
     * @param type
     * @param file
     * @param authorization
     * @return
     */
    @RequestMapping(value = "${yuntian.image.upload.url}"+"/true",method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String imageUpload(
            @RequestParam("type") int type,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization")String authorization
    );


    /**
     * 获取上传图片之后的小图像
     * @param id
     * @param authorization
     * @return
     */
    @RequestMapping(value ="${yuntian.image.small.url}" + "/{id}",method = RequestMethod.GET)
    String getUploadImageSmall(@PathVariable("id") String id,
                     @RequestHeader("Authorization")String authorization);

    /**
     * 图片1:1 调用
     * @param params
     * @param authorization
     * @return
     */
    @RequestMapping(value ="${yuntian.image.compare.url}",
            method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    String faceImageCompare(
            String params,
            @RequestHeader("Authorization")String authorization
    );


    /**
     * 图像视频比对
     * @param video
     * @param deviceId
     * @param authorization
     * @return
     */
    @RequestMapping(value = "${yuntian.anjuxing.upload.url}",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String faceVideoCompare(
            @RequestPart("fileVideo") MultipartFile video ,
            @RequestParam("deviceId") int deviceId,
            @RequestHeader("Authorization")String authorization);

    /**
     * 从图像库中搜索相似的图像
     * @param params
     * @param authorization
     * @return
     */
    @RequestMapping(value ="${yuntian.image.search.url}",
            method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    String searchImages(
            String params,
            @RequestHeader("Authorization")String authorization
    );






}
