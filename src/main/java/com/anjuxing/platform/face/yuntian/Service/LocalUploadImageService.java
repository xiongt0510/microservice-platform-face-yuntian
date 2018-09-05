package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.UploadImageResult;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author xiongt
 * @Description
 */
public interface LocalUploadImageService {

    UploadImageResult getUploadImageResult(MultipartFile file, UploadType uploadType) throws IOException;

    UploadImageResult getUploadImageResult(File file ,UploadType uploadType);
}
