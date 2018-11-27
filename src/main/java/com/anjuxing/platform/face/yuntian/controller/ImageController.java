package com.anjuxing.platform.face.yuntian.controller;

import com.anjuxing.platform.face.yuntian.Service.ImageService;
import com.anjuxing.platform.face.yuntian.model.*;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import com.anjuxing.platform.face.yuntian.util.ImageUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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


    @PostMapping("/multiple/search")
    public String imageMultiSearch(@RequestParam("communityId") String communityId ,
                                   @RequestParam("houseId") String houseId ,
                                   @RequestParam("threshold") Double threshold,
                                   @RequestParam("peopleId") String peopleId,
                                   @RequestParam("photos")String photoJson) throws IOException {

        ImageMultiRequestParam param = new ImageMultiRequestParam();
        param.setCommunityId(communityId);
        param.setHouseId(houseId);
        param.setPeopleId(peopleId);
        param.setThreshold(threshold);

        JsonNode jsonNode = objectMapper.readTree(photoJson);

        if (jsonNode.isArray()){
           List<PhotoParam> photoParams =  objectMapper.readValue(String.valueOf(jsonNode),new TypeReference<List<PhotoParam>>(){});
           if (Objects.nonNull(photoParams) && photoParams.size() > 0){

               for (PhotoParam photoParam : photoParams){
                   photoParam.setBase64(ImageUtil.getImageStrFromUrl(photoParam.getPhotoUrl()));
               }
               param.setPhotos(objectMapper.writeValueAsString(photoParams));
           }
        }

        if (StringUtils.isEmpty(param.getPhotos())){
            return "error";
        }

      return  imageService.imageMultiSearch(param);
    }

    private class PhotoParam{

        private String id;

        @JsonIgnore
        private String photoUrl;

        private String base64;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getBase64() {
            return base64;
        }

        public void setBase64(String base64) {
            this.base64 = base64;
        }
    }

}
