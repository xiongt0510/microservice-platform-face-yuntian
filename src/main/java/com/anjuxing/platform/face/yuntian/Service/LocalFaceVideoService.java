package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.FaceVideoResult;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author xiongt
 * @Description
 */
public interface LocalFaceVideoService  {

    FaceVideoResult getFaceVideoResult(MultipartFile fileVideo,MultipartFile fileImage,int deviceId) throws Exception;
}
