package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.FaceCompareResult;

import java.io.IOException;

/**
 * @author xiongt
 * @Description
 */
public interface LocalFaceService {

    FaceCompareResult loadFaceCompare(long faceIdA , long faceIdB) throws IOException;
}
