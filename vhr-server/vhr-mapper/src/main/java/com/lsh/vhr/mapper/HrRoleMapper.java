package com.lsh.vhr.mapper;

import com.lsh.vhr.model.Hr;
import org.apache.ibatis.annotations.Param;
import com.lsh.vhr.model.HrRole;

import java.util.List;

public interface HrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrRole record);

    int insertSelective(HrRole record);

    HrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrRole record);

    int updateByPrimaryKey(HrRole record);

    void deleteByHrid(Integer hrid);

    Integer addRole(@Param("hrid") Integer hrid, @Param("rids") Integer[] rids);

    List<Hr> getAllHrsExceptCurrentHr(Integer id);
}