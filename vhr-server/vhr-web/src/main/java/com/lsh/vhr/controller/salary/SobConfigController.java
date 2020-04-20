package com.lsh.vhr.controller.salary;

import com.lsh.vhr.model.Employee;
import com.lsh.vhr.model.RespBean;
import com.lsh.vhr.model.RespPageBean;
import com.lsh.vhr.model.Salary;
import com.lsh.vhr.service.EmployeeService;
import com.lsh.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary/sobconf")
public class SobConfigController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer page
            ,@RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeByPageWithSalary(page,size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(@RequestParam("eid") Integer eid,@RequestParam("sid") Integer sid){
        Integer result = employeeService.updateEmployeeSalaryById(eid, sid);
        if (result==1||result==2){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
