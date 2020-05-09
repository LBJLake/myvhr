package com.lsh.vhr.config;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class FastDFSUtils {
    private static StorageClient1 client1;

    static {
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            client1 = new StorageClient1(trackerServer, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public static String upload(MultipartFile file) {
        String oldName = file.getOriginalFilename();
        try {
            return client1.upload_file1(file.getBytes(), oldName.substring(oldName.lastIndexOf(".") + 1), null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String uploadWithToken(String filePath, String nginxHost, String secretKey) {
        int ts = ((int) Instant.now().getEpochSecond());
        char[] chars = filePath.toCharArray();
        String filePath2 = null;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '/') {
                filePath2 = filePath.substring(i + 1);
                break;
            }
        }

        try {
            String token = ProtoCommon.getToken(filePath2, ts, secretKey);
            StringBuffer sb = new StringBuffer();
            sb.append(nginxHost)
                    .append(filePath)
                    .append("?token=")
                    .append(token)
                    .append("&ts=")
                    .append(ts);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }

}
