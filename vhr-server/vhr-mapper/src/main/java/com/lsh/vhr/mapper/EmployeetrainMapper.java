package com.lsh.vhr.mapper;

import com.lsh.vhr.model.Employeetrain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeetrainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeetrain record);

    int insertSelective(Employeetrain record);

    Employeetrain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeetrain record);

    int updateByPrimaryKey(Employeetrain record);

    List<Employeetrain> getAllEmployeetrains(@Param("keyword") String keyword);
}