package com.lsh.vhr.service;

import com.lsh.vhr.mapper.NationMapper;
import com.lsh.vhr.mapper.PoliticsstatusMapper;
import com.lsh.vhr.model.Nation;
import com.lsh.vhr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationService {
    @Autowired
    NationMapper nationMapper;
    @Autowired
    PoliticsstatusMapper politicsstatusMapper;
    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }


}
