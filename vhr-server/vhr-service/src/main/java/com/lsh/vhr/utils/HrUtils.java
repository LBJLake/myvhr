package com.lsh.vhr.utils;

import com.lsh.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

public class HrUtils {
    private HrUtils(){}
    public static Hr getCurrentHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
