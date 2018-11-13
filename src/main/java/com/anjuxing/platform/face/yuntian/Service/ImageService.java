package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.*;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author xiongt
 * @Description
 */

public interface ImageService {

    /**
     * 上传图像
     * @param file
     * @param uploadType
     * @return
     * @throws IOException
     */
    UploadImageResult uploadImage(MultipartFile file, UploadType uploadType) throws IOException;

    /**
     * 抓取小图像
     * @param bigImageId
     * @return
     */
    List<SmallImageResult> fetchSmallImage(String bigImageId) throws IOException;


    /**
     * 图像1：1比较
     * @param faceIdA
     * @param faceIdB
     * @return
     * @throws IOException
     */
    ImageCompareResult imagesCompare(long faceIdA , long faceIdB) throws IOException;

    /**
     * 图像视频比较
     * @param fileVideo
     * @param fileImage
     * @param deviceId
     * @return
     * @throws Exception
     */
    ImageVideoCompareResult imageVideoCompare(MultipartFile fileVideo, MultipartFile fileImage, int deviceId) throws Exception;


    /**
     * 图像搜索
     * @return
     */
    String imageSearch(String communityId,
                                  String threshold,String imageUrl,int size) throws IOException;
}
