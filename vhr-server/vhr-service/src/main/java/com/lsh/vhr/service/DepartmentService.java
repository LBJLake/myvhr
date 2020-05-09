package com.lsh.vhr.service;

import com.lsh.vhr.mapper.DepartmentMapper;
import com.lsh.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @Transactional
    public void addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
    }

    @Transactional
    public void deleteDepById(Department dep) {
        departmentMapper.deleteDepById(dep);
    }

    public List<Department> getAllDepartmentsWithoutChildren() {
        return departmentMapper.getAllDepartmentsWithOutChildren();
    }
}
