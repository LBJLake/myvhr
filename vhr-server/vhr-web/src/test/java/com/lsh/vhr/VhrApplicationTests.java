package com.lsh.vhr;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@SpringBootTest
class VhrApplicationTests {

    @Test
    void contextLoads() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer=null;
        StorageClient1 client1 = new StorageClient1(trackerServer, storageServer);
        NameValuePair pairs[] = null;
        String fileId = client1.upload_file1("D:\\number.jpg", "jpg", pairs);
        System.out.println(fileId);


    }

    @Test
    void download() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer=null;
        StorageClient1 client1 = new StorageClient1(trackerServer, storageServer);
        NameValuePair pairs[] = null;
        byte[] bytes = client1.download_file1("group1/M00/00/00/wKgfgV6bz76AVps2AABKZpVJp2U291.jpg");
        FileOutputStream fos = new FileOutputStream(new File("D:\\number-2.jpg"));
        fos.write(bytes);
        fos.close();

    }

    @Test
    void testToken() throws UnsupportedEncodingException, NoSuchAlgorithmException, MyException {
        int ts=((int) Instant.now().getEpochSecond());
        String token = ProtoCommon.getToken("M00/00/00/wKgfgV6cAJSAE5MlAABKZpVJp2U242.jpg", ts, "FastDFS1234567890");
        StringBuffer sb = new StringBuffer();
        sb.append("http://192.168.31.129")
                .append("/group1/M00/00/00/wKgfgV6cAJSAE5MlAABKZpVJp2U242.jpg")
                .append("?token=")
                .append(token)
                .append("&ts=")
                .append(ts);

    }

}
