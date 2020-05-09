package com.lsh.vhr.service;

import com.lsh.vhr.mapper.HrMapper;
import com.lsh.vhr.mapper.HrRoleMapper;
import com.lsh.vhr.model.Hr;
import com.lsh.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class HrService implements UserDetailsService {
    @Autowired
    private HrMapper hrMapper;
    @Autowired
    HrRoleMapper hrRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null) {
            throw new UsernameNotFoundException("用户名不存在！");

        }
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    public List<Hr> getAllHrs(String keywords) {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(), keywords);
    }

    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrid);
        if (rids == null) return true;
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }

    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }

    public List<Hr> getAllHrsExceptCurrentHr() {
        return hrMapper.getAllHrsExceptCurrentHr(HrUtils.getCurrentHr().getId());
    }

    public Integer updateHrPassword(Map<String, Object> info) {
        Integer hrid = (Integer) info.get("hrid");
        Hr hr = hrMapper.selectByPrimaryKey(hrid);
        String oldpass = (String) info.get("oldpass");
        String pass = (String) info.get("pass");
        String checkPass = (String) info.get("checkPass");
        if (oldpass == null || oldpass.equals("") || pass == null || pass.equals("") || checkPass == null || checkPass.equals(""))
            return 0;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldpass, hr.getPassword()) && checkPass.equals(pass)) {//matchs第一个参数为明文密码，第二个为匿文密码
            String encodePass = encoder.encode(pass);
            return hrMapper.updatePasswd(hr.getId(), encodePass);
        } else return 0;
    }

    public Integer updateUserface(String url, Integer id) {
        return hrMapper.updateUserface(url, id);
    }
}
