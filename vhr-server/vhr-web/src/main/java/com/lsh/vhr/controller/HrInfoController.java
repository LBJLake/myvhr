package com.lsh.vhr.controller;

import com.lsh.vhr.config.FastDFSUtils;
import com.lsh.vhr.model.Hr;
import com.lsh.vhr.model.RespBean;
import com.lsh.vhr.service.HrService;
import com.lsh.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@PropertySource("fastdfs-client.properties")
public class HrInfoController {
    @Autowired
    HrService hrService;
    @Value("${fastdfs.nginx.host}")
    String nginxHost;
    @Value("${fastdfs.http_secret_key}")
    String secretKey;

    @GetMapping("/hr/info")
    public Hr getCurrentHr(Authentication authentication) {
        return ((Hr) authentication.getPrincipal());
    }

    @PutMapping("/hr/info")
    public RespBean updateHr(@RequestBody Hr hr, Authentication authentication) {
        if (hrService.updateHr(hr) == 1) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr
                    , authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @PutMapping("/hr/pass")
    public RespBean updateHrPassword(@RequestBody Map<String, Object> info) {
        if (hrService.updateHrPassword(info) == 1) {

            return RespBean.ok("修改密码成功！");
        }
        return RespBean.error("修改密码失败！");
    }

    @PostMapping("/hr/userface")
    public RespBean updateHrUserface(MultipartFile file, Integer id, Authentication authentication) {
        String fileId = FastDFSUtils.upload(file);
        String url = FastDFSUtils.uploadWithToken(fileId, nginxHost, secretKey);
        if (hrService.updateUserface(url, id) == 1) {
            Hr hr = (Hr) authentication.getPrincipal();
            hr.setUserface(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr, authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.ok("更新成功!", url);
        }
        return RespBean.error("更新失败!");
    }

}
