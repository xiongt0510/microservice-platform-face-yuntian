package com.anjuxing.platform.face.yuntian.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @author xiongt
 * @Description
 */
@RestController
public class TestController {

    @Autowired
    private FeignTest feign;

    @GetMapping("/test")
    public String getTest(){

        File file = new File("C:\\Users\\Administrator\\20180904101108.png");

        String key = "20180904101108png";

       return feign.storeResult(file,key);

    }


    @GetMapping("/test/person")
    public String addPerson(){
//        FaceNewPersonRequest personNewReq = new FaceNewPersonRequest(
//                person.getBucketName(),
//                person.getPersonId(),
//                person.getGroupIds(),
//                person.getUrl(),
//                person.getPersonName(),
//                person.getTag());

        Person person = new Person();

//        person.setUrl("C:\\Users\\Administrator\\20180904101108.png");
        person.setFile(new File("C:\\Users\\Administrator\\20180904101108.png"));
        person.setPersonId("20180904101108");
        person.setGroupIds(new String[]{"person1"});
        person.setPersonName("乔布斯");
        person.setTag("头像");

        return feign.putPerson(person);
    }

    @GetMapping("/verify")
    public  String verify(){

        String bucketName = "";

        String url = "http://anjuxing-1257190036.cos.ap-guangzhou.myqcloud.com/20180904101108png?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDnUfjcJ6GZq04z3rfDcpqhDbnGnAR5YKv%26q-sign-time%3D1536198841%3B1541469241%26q-key-time%3D1536198841%3B1541469241%26q-header-list%3D%26q-url-param-list%3D%26q-signature%3D07f194381707a2b91554a1f24397d494c9707bbc";

        String personId = "20180904101108";


       return feign.verify(personId,url);
    }
}
