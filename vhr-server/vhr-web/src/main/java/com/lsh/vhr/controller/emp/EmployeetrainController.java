package com.lsh.vhr.controller.emp;

import com.lsh.vhr.model.Employee;
import com.lsh.vhr.model.Employeetrain;
import com.lsh.vhr.model.RespBean;
import com.lsh.vhr.model.RespPageBean;
import com.lsh.vhr.service.EmployeeTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/personnel/train")
public class EmployeetrainController {
    @Autowired
    EmployeeTrainService employeeTrainService;

    @GetMapping("/")
    public List<Employeetrain> getAllEmployeeTrains(@RequestParam(value = "keyword", required = false) String keyword) {
        return employeeTrainService.getAllEmployeeTrains(keyword);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeTrainService.getEmployees();
    }

    @PostMapping("/")
    public RespBean addEmployeetrain(@RequestBody Employeetrain employeetrain) {
        if (employeeTrainService.addEmployeetrain(employeetrain) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @PutMapping("/")
    public RespBean updateEmployeetrain(@RequestBody Employeetrain employeetrain) {
        if (employeeTrainService.updateEmployeetrain(employeetrain) == 1) {
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmployeetrain(@PathVariable Integer id) {
        if (employeeTrainService.deleteEmployeetrain(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
