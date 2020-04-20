package com.lsh.vhr.controller.config;

import com.lsh.vhr.model.Menu;
import com.lsh.vhr.model.RespBean;
import com.lsh.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
    @Autowired
    MenuService menuService;
    @GetMapping("/menu")
    public List<Menu> getMenusByHrId(){
        return menuService.getMenusByHrId();
    }
}
