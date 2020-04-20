package com.lsh.vhr.service;

import com.lsh.vhr.mapper.EmployeeMapper;
import com.lsh.vhr.mapper.EmployeetrainMapper;
import com.lsh.vhr.model.Employee;
import com.lsh.vhr.model.Employeetrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeTrainService {
    @Autowired
    EmployeetrainMapper employeetrainMapper;
    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employeetrain> getAllEmployeeTrains(String keyword) {
        return employeetrainMapper.getAllEmployeetrains(keyword);
    }

    public List<Employee> getEmployees() {
        return employeeMapper.getEmployees();
    }

    public Integer addEmployeetrain(Employeetrain employeetrain) {
        return employeetrainMapper.insertSelective(employeetrain);
    }

    public Integer updateEmployeetrain(Employeetrain employeetrain) {
        return employeetrainMapper.updateByPrimaryKeySelective(employeetrain);
    }

    public Integer deleteEmployeetrain(Integer id) {
        return employeetrainMapper.deleteByPrimaryKey(id);
    }
}

