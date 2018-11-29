package com.anjuxing.platform.face.yuntian.controller;

import com.anjuxing.platform.face.yuntian.Service.ImageService;
import com.anjuxing.platform.face.yuntian.model.*;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import com.anjuxing.platform.face.yuntian.util.ImageUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.anjuxing.platform.face.yuntian.model.ImageMultiRequestParam.PhotoRequest;

import java.io.IOException;
import java.util.ArrayList;
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
        param.setHouseCode(houseId);
        param.setPeopleId(peopleId);
        param.setThreshold(threshold);

        JsonNode jsonNode = objectMapper.readTree(photoJson);

        if (jsonNode.isArray()){
           List<PhotoParam> photoParams =  objectMapper.readValue(String.valueOf(jsonNode),new TypeReference<List<PhotoParam>>(){});
           if (Objects.nonNull(photoParams) && photoParams.size() > 0){
                List<PhotoRequest> photoRequests = new ArrayList<>();
               for (PhotoParam photoParam : photoParams){
                   if (StringUtils.isEmpty(photoParam.getPhotoUrl())) continue;
                   PhotoRequest photoRequest = new PhotoRequest();
                   photoRequest.setId(photoParam.getId());
                   photoRequest.setBase64(ImageUtil.getImageStrFromUrl(photoParam.getPhotoUrl()));
                   photoRequests.add(photoRequest);
               }

               param.setPhotos(photoRequests);
           }



        }

        if (StringUtils.isEmpty(param.getPhotos())){
            return "error";
        }

      return  imageService.imageMultiSearch(param);
    }

    private static class PhotoParam{
        private String id ;
        private String photoUrl;

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
    }


}
