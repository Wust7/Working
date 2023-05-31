package com.cug.resource.controller;

import com.cug.resource.service.MonitorService;
import com.cug.resource.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;

@RestController
@RequestMapping("monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    // 返回水环境全部资源
    @GetMapping("/list")
    public R allComment(){
        HashSet<ArrayList<String>> set = monitorService.queryPage();
        return R.ok().put("data",set);

    }
}
