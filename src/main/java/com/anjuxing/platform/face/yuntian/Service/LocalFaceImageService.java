package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.SmallFaceImageResult;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface LocalFaceImageService {

    List<SmallFaceImageResult> fetchSmallFaceImage(String bigImageId);
}
