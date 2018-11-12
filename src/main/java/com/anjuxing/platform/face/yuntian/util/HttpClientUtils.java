package com.anjuxing.platform.face.yuntian.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiongt
 * @Description
 */
public class HttpClientUtils {


    public static String httpResponseString(String url,String authorization,HttpEntity entity) throws IOException {

        String respContent = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Authorization",authorization);

        httpPost.setEntity(entity);

        HttpResponse resp = httpclient.execute(httpPost);
        if(resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he,"UTF-8");
        }
        return respContent;
    }

    /**
     * 文件形式上传
     * @param files
     * @return
     */
    private static MultipartEntityBuilder multipartEntityBuilder(Map<String,MultipartFile> files){
        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.setCharset(java.nio.charset.Charset.forName("UTF-8"));
        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        if (Objects.nonNull(files)){
            files.forEach((key, value) -> {
                        try {
                            reqEntity.addBinaryBody(key, value.getInputStream(), ContentType.MULTIPART_FORM_DATA, value.getOriginalFilename());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );

        }
        return reqEntity;
    }


    private static StringEntity jsonStringEntity(String jsonParam){
        StringEntity stringEntity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
        return stringEntity;
    }

    private static UrlEncodedFormEntity formEntity(Map<String,String> params) throws UnsupportedEncodingException {
        List<NameValuePair> pairs = new ArrayList<>();
        if (Objects.nonNull(params)){
            params.forEach((key,value)->
              pairs.add(new BasicNameValuePair(key,value))
            );
        }

        return  new UrlEncodedFormEntity(pairs, HTTP.UTF_8);
    }

    public static String callRemote4Multipart(String url,String authorization,
    Map<String,MultipartFile> files,Map<String,String> params) throws IOException {
        MultipartEntityBuilder entityBuilder = multipartEntityBuilder(files);
        if (Objects.nonNull(params)){
            params.forEach((key,value) ->
                    entityBuilder.addTextBody(key,value)
            );
        }
        return httpResponseString(url,authorization,entityBuilder.build());
    }

    public static String callRemote4JsonString(String url,String authorization,String jsonString) throws IOException {
        StringEntity entity = jsonStringEntity(jsonString);
        return  httpResponseString(url,authorization,entity);
    }


    public static String callRemote4FormString(String url,String authorization,Map<String,String> params) throws IOException {
        UrlEncodedFormEntity entity = formEntity(params);
        return httpResponseString(url,authorization,entity);

    }

}
