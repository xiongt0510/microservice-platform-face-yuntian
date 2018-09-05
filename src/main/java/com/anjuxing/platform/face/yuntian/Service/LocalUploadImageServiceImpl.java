package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.Service.remote.RemoteUploadImageService;
import com.anjuxing.platform.face.yuntian.model.UploadImageResult;
import com.anjuxing.platform.face.yuntian.properties.UploadType;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author xiongt
 * @Description
 */
@Service
public class LocalUploadImageServiceImpl implements LocalUploadImageService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RemoteUploadImageService remoteUploadImageService;

    @Autowired
    private RedisRepository redis;

    @Autowired
    private YuntianPropeties yt;

    @Autowired
    private LocalTokenService localTokenService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public UploadImageResult getUploadImageResult(MultipartFile file , UploadType uploadType) throws IOException {

        //如果redis 中没有token 就重新获取并存储到redis 中
        if (StringUtils.isEmpty(redis.getAccessToken())){
            localTokenService.getLocalToken(yt.getClient());
        }

        String authorization = YuntianConstanse.AUTHORIZATION_BERAER+ redis.getAccessToken();

        String datas = remoteUploadImageService.imageUpload(uploadType.getValue(),file,authorization);

        logger.info("return remote data :" + datas);

        DataResult dataResult = mapper.readValue(datas,DataResult.class);

        return dataResult.uploadImage();
    }

    @Override
    public UploadImageResult getUploadImageResult(File file , UploadType uploadType) {
        return null;
    }

    private class DataResult {
        private String data;

        private int errCode;

        private int maxPage;

        private int total;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getErrCode() {
            return errCode;
        }

        public void setErrCode(int errCode) {
            this.errCode = errCode;
        }

        public int getMaxPage() {
            return maxPage;
        }

        public void setMaxPage(int maxPage) {
            this.maxPage = maxPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        private UploadImageResult uploadImage() throws IOException {

            UploadImageResult uploadImage = null;
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(data)){
                uploadImage = mapper.readValue(data,UploadImageResult.class);
            }

            return  Objects.nonNull(uploadImage) ? uploadImage : new UploadImageResult();

        }
    }
}
